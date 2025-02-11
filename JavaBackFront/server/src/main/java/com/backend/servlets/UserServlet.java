package com.backend.servlets;

import java.io.IOException;
import java.util.Map;

import com.backend.dal.dao.DataContext;
import com.backend.rest.RestResponse;
import com.backend.rest.RestService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class UserServlet extends HttpServlet {
    private final DataContext dataContext;
    private final RestService restService;

    @Inject
    public UserServlet(DataContext dataContext, RestService restService) {
        this.dataContext = dataContext;
        this.restService = restService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        RestResponse restResponse = new RestResponse()
                .setResourceUrl("GET /user")
                .setCashTime(600)
                .setMeta(Map.of(

                        "DataType", "object",
                        "read", "GET /user",
                        "update", "PUT /user",
                        "delete", "DELETE /user"));

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null) {

            restService.sendResponse(res, restResponse.setStatus(401)
                    .setData("Authorization header required"));
            return;

        }

        String authScheme = "Basic ";

        if (!authHeader.startsWith(authScheme)) {
            restService.sendResponse(res, restResponse.setStatus(401)
                    .setData("Authorization cheme error "));
            return;
        }

        String credentials = authHeader.substring(authScheme.length());

        restResponse.setData(credentials);

        restService.sendResponse(res, restResponse);

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        restService.setCorsHeaders(resp);
    }

}
