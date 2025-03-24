package com.backend.dal.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.checkerframework.checker.units.qual.radians;

public class Cart {

    private UUID ucardId;
    private UUID UserAccessId;
    private byte isCardCencelled;
    private double cardPrice;
    private Date CardCreateAt;
    private Date CardCloseAt;

    private List<CartItem> cartItems;

   

    public static Cart fromResulSet(ResultSet rs) throws SQLException {

        Cart cart = new Cart();

        cart.setUcardId(UUID.fromString(rs.getString("card_id")));
        cart.setUserAccessId(UUID.fromString(rs.getString("user_access_id")));
        java.sql.Timestamp timestamp=rs.getTimestamp("card_create_at");
        cart.setCardCreateAt(timestamp==null?null:new Date(timestamp.getTime()));
        timestamp=rs.getTimestamp("card_close_at");
        cart.setCardCloseAt(timestamp==null?null:new Date(timestamp.getTime()));
        cart.setCardPrice(rs.getDouble("card_price"));
        cart.setIsCardCencelled(rs.getByte("card_is_cancelled"));
        
        CartItem cartItem=null;
        try{
            cartItem=CartItem.fromResulSet(rs);
        
        }catch(Exception ignored){}

        if (cartItem!=null) {


            cart.cartItems=new ArrayList<>();
            cart.cartItems.add(cartItem);

            while (rs.next()) {
                cart.cartItems.add(CartItem.fromResulSet(rs));
            }
            
        }

     
        return cart;

    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public UUID getUcardId() {
        return ucardId;
    }

    public void setUcardId(UUID ucardId) {
        this.ucardId = ucardId;
    }

    public UUID getUserAccessId() {
        return UserAccessId;
    }

    public void setUserAccessId(UUID userAccessId) {
        UserAccessId = userAccessId;
    }

    public byte getIsCardCencelled() {
        return isCardCencelled;
    }

    public void setIsCardCencelled(byte isCardCencelled) {
        this.isCardCencelled = isCardCencelled;
    }

    public double getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(double cardPrice) {
        this.cardPrice = cardPrice;
    }

    public Date getCardCreateAt() {
        return CardCreateAt;
    }

    public void setCardCreateAt(Date cardCreateAt) {
        CardCreateAt = cardCreateAt;
    }

    public Date getCardCloseAt() {
        return CardCloseAt;
    }

    public void setCardCloseAt(Date cardCloseAt) {
        CardCloseAt = cardCloseAt;
    }

}

