package com.backend.dal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.backend.dal.dto.Cart;
import com.backend.dal.dto.CartItem;
import com.backend.dal.dto.Product;
import com.backend.services.db.DbService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CartDao {

    private final DbService dbService;
    private final Logger logger;
    private final SimpleDateFormat sqSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Inject
    public CartDao(DbService dbService, Logger logger) {

        this.logger = logger;
        this.dbService = dbService;

    }

    public Cart geCart(UUID cartId) {

        String sql = "SELECT * FROM carts c "
                + "JOIN carts_item ci ON c.card_id=ci.ucard_id "
                + "WHERE c.card_id=?";

        try (PreparedStatement prep = dbService.getConnection().prepareStatement(sql)) {
            prep.setString(1, cartId.toString());
            ResultSet rs = prep.executeQuery();
            if (rs.next())
                return Cart.fromResulSet(rs);

        } catch (SQLException ex) {

            logger.log(Level.WARNING, "CartDao::geCart {0} sql {1}", new Object[] { ex.getMessage(), sql });

        }
        return null;

    }

    public boolean addToCart(Cart cart, Product product) {

        CartItem cartItem;
        boolean isNew;
        String sql = "SELECT * FROM carts_item ci WHERE ci.ucard_id=? AND ci.product_id= ?";

        try (PreparedStatement prep = dbService.getConnection().prepareStatement(sql)) {
            prep.setString(1, cart.getUcardId().toString());
            prep.setString(2, product.getProductId().toString());
            ResultSet rs = prep.executeQuery();
            double addedPrice = product.getPrice();
            if (rs.next()) {

                cartItem = CartItem.fromResulSet(rs);

                cartItem.setQuantity((short) (cartItem.getQuantity() + 1));
                cartItem.setCardItemPrice(cartItem.getCardItemPrice() + product.getPrice());
                cart.setCardPrice(cart.getCardPrice());
                isNew = false;
            } else {

                cartItem = new CartItem();

                cartItem.setCardItemId(UUID.randomUUID());
                cartItem.setUcardId(cart.getUcardId());
                cartItem.setProductId(product.getProductId());
                cartItem.setQuantity((short) 1);
                cartItem.setCardItemPrice(addedPrice);
                isNew = true;
            }

            cart.setCardPrice(cart.getCardPrice() + addedPrice);
        } catch (SQLException ex) {

            logger.log(Level.WARNING, "CartDao::addToCart {0} sql {1}", new Object[] { ex.getMessage(), sql });
            return false;
        }

        sql = isNew
                ? "INSERT INTO carts_item (ucard_id,product_id,quantity, card_item_price, card_item_id) VALUES(?,?,?,?,?)"
                : "UPDATE  carts_item SET  ucard_id=?, product_id=?,quantity=?,card_item_price=? WHERE card_item_id=? ";

        try (PreparedStatement prep = dbService.getConnection().prepareStatement(sql)) {
            prep.setString(1, cartItem.getUcardId().toString());
            prep.setString(2, product.getProductId().toString());
            prep.setShort(3, cartItem.getQuantity());
            prep.setDouble(4, cartItem.getCardItemPrice());
            prep.setString(5, cartItem.getCardItemId().toString());

            prep.executeUpdate();
            prep.getConnection().commit();
        } catch (SQLException ex) {

            logger.log(Level.WARNING, "CartDao::addToCart {0} sql {1}", new Object[] { ex.getMessage(), sql });
            return false;
        }
        sql = "UPDATE carts SET card_price = ? WHERE card_id = ?";
        try (PreparedStatement prep = dbService.getConnection().prepareStatement(sql)) {
            prep.setDouble(1, cart.getCardPrice());
            prep.setString(2, cart.getUcardId().toString());
            prep.executeUpdate();
            dbService.getConnection().commit();
        } catch (SQLException ex) {
            logger.log(
                    Level.WARNING,
                    "CartDao::addToCart {0} sql: {1}",
                    new Object[] { ex.getMessage(), sql });
            return false;
        }
        return true;
    }

    public Cart getUserCart(UUID userAccessId, boolean createNew) {

        String sql = String.format(Locale.ROOT,
                "SELECT * FROM carts c WHERE c.card_close_at IS NULL AND c.user_access_id='%s'",
                userAccessId.toString());

        try (Statement stmt = dbService.getConnection().createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {

                return Cart.fromResulSet(rs);

            } else if (createNew) {

                Cart cart = new Cart();
                cart.setUcardId(UUID.randomUUID());
                cart.setUserAccessId(userAccessId);
                cart.setCardCreateAt(new Date());

                sql = String.format(Locale.ROOT,
                        "INSERT INTO carts (card_id, user_access_id, card_create_at, card_price) VALUES ('%s','%s','%s',0)",
                        cart.getUcardId().toString(),
                        userAccessId.toString(),
                        sqSimpleDateFormat.format(cart.getCardCreateAt()));

                stmt.executeUpdate(sql);
                dbService.getConnection().commit();
                return cart;
            }

        } catch (SQLException ex) {

            logger.log(Level.WARNING, "CartDao::getUserCart {0} sql {1}", new Object[] { ex.getMessage(), sql });

        }
        return null;
    }

    public boolean installCarts() {

        Future<Boolean> task1 = CompletableFuture.supplyAsync(this::installCart);
        Future<Boolean> task2 = CompletableFuture.supplyAsync(this::installCartItems);
        try {
            boolean res1 = task1.get();
            boolean res2 = task2.get();
            try {
                dbService.getConnection().commit();
            } catch (SQLException ignore) {
            }
            ;
            return res1 && res2;
        } catch (Exception e) {

            logger.log(Level.WARNING, "The super puper Async {0}", e.getMessage());
            return false;
        }

    }

    private boolean installCart() {

        String sql = "CREATE TABLE IF NOT EXISTS carts("
                + "card_id            CHAR(36)     PRIMARY KEY DEFAULT( UUID() ),"
                + "user_access_id     CHAR(36)       NOT  NULL,"
                + "card_is_cancelled  TINYINT             NULL,"
                + "card_price         DECIMAL(14,2)  NOT  NULL,"
                + "card_create_at     DATETIME       NOT  NULL,"
                + "card_close_at      DATETIME            NULL"
                + ") Engine = InnoDB, DEFAULT CHARSET = utf8mb4";

        try (Statement statement = dbService.getConnection().createStatement()) {

            statement.executeUpdate(sql);
            logger.info("CartDao Ok");
            return true;

        } catch (SQLException ex) {

            logger.log(Level.WARNING, "CartDao::installCard {0} sql {1}", new Object[] { ex.getMessage(), sql });
            return false;

        }

    }

    private boolean installCartItems() {

        String sql = "CREATE TABLE IF NOT EXISTS carts_item("
                + "card_item_id      CHAR(36)     PRIMARY KEY DEFAULT( UUID() ),"
                + "ucard_id          CHAR(36)       NOT  NULL,"
                + "product_id        CHAR(36)       NOT  NULL,"
                + "card_item_price   DECIMAL(12,2)  NOT  NULL,"
                + "quantity          SMALLINT       NOT  NULL DEFAULT 1,"
                + "action_id         CHAR(36)            NULL"
                + ") Engine = InnoDB, DEFAULT CHARSET = utf8mb4";

        try (Statement statement = dbService.getConnection().createStatement()) {

            statement.executeUpdate(sql);
            logger.info("CardItems Ok");
            return true;

        } catch (SQLException ex) {

            logger.log(Level.WARNING, "CardDao::installCardItems {0} sql {1}", new Object[] { ex.getMessage(), sql });
            return false;

        }
    }
}
