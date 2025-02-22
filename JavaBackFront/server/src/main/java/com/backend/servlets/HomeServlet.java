package com.backend.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.backend.dal.dao.DataContext;
import com.backend.dal.dto.User;
import com.backend.models.UserSignUpFormModel;
import com.backend.rest.RestResponse;
import com.backend.rest.RestService;
import com.backend.services.db.DbService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Singleton
public class HomeServlet extends HttpServlet {

    private final DataContext dataContext;
    private final RestService restService;

    @Inject
    public HomeServlet(RestService restService, DataContext dataContext, DbService dbService) {

        this.dataContext = dataContext;
        this.restService = restService;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String message;
        message = dataContext.getUserDao().installTables()&&dataContext.getAccessTokenDao().installTables() ? "Install OK" : "Install fail";
        restService.sendResponse(resp, new RestResponse()
                .setResourceUrl("POST /home")
                .setStatus(200)
                .setMessage(message)
                .setMeta(Map.of(

                        "DataType", "object",
                        "read", "GET /home",
                        "update", "PUT /home",
                        "delete", "DELETE /home")));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserSignUpFormModel model;
        RestResponse restResponse = new RestResponse()
                .setResourceUrl("POST /home")
                .setCashTime(0);

        try {

            model = restService.fromBody(req, UserSignUpFormModel.class);

        } catch (Exception ex) {
            restService.sendResponse(resp, restResponse
                    .setStatus(422)
                    .setMessage(ex.getMessage()));
            return;
        }

        User user = dataContext.getUserDao().addUser(model);

        if (user == null) {

            restService.sendResponse(resp, restResponse
                    .setStatus(507)
                    .setMessage("DB error")
                    .setData(model));

        } else {
            restService.sendResponse(resp, restResponse
                    .setStatus(201)
                    .setMessage("Create")
                    .setData(user));

        }

    }

    // Setting cors
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        restService.setCorsHeaders(resp);
    }

}
