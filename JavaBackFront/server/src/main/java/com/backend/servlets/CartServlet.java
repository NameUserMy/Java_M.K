package com.backend.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.backend.dal.dao.DataContext;
import com.backend.dal.dto.Cart;
import com.backend.dal.dto.Product;
import com.backend.dal.dto.UserAccess;
import com.backend.rest.RestResponse;
import com.backend.rest.RestService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class CartServlet extends HttpServlet {
    private final DataContext dataContext;
    private final RestService restService;

    @Inject
    public CartServlet(DataContext dataContext, RestService restService) {

        this.dataContext = dataContext;
        this.restService = restService;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RestResponse restResponse = new RestResponse()
                .setResourceUrl("GET /cart")
                .setMeta(Map.of(
                        "DataType", "string",
                        "read", "GET /cart",
                        "update", "PUT /cart",
                        "delete", "DELETE /cart"));

        UserAccess userAccess = (UserAccess) req.getAttribute("AuthUserAccess");

        if (userAccess == null) {

            restService.sendResponse(resp,
                    restResponse.setStatus(401).setData(req.getAttribute("AuthStatus")));

            return;

        }

        UUID productId;
        try {

            productId = UUID.fromString(req.getParameter("productId"));
        } catch (Exception ignored) {

            restService.sendResponse(resp,
                    restResponse.setStatus(400).setData("Parametr 'productId' unprocessable"));

            return;

        }
        Product product = dataContext.getProductDao().geProductById(productId);

        if (product == null) {

            restService.sendResponse(resp,
                    restResponse.setStatus(404).setData("Product not found"));

            return;

        }

        Cart cart = dataContext.getCartDao().getUserCart(userAccess.getUserAccessId(), true);
        if (dataContext.getCartDao().addToCart(cart, product)) {
            restService.sendResponse(resp,
                    restResponse.setStatus(202)
                    .setData(dataContext.getCartDao().geCart(cart.getUcardId())));

        } else {

            restService.sendResponse(resp,
                    restResponse.setStatus(500).setData("See server logs"));

        };

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        restService.setCorsHeaders(resp);
    }

}

/*
 * АРІ авторизованого користувача на прикладі замовлень/кошику
 * 1) Структура даних
 * [UserAccess] [carts] [cart_items]
 * id --------\ cart_id ------\ cart_item_id
 * \--- user_access_id \--- cart_id
 * created_at product_id ------- [products]
 * closed_at cart_item_price
 * is_cancelled action_id --------- [actions]
 * cart_price quantity
 */
