package com.backend.servlets;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import com.backend.dal.dao.DataContext;
import com.backend.dal.dto.User;
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
        try {
            credentials = new String(Base64.getDecoder().decode(credentials.getBytes()));
        } catch (Exception ex) {

            restService.sendResponse(res, restResponse.setStatus(422)
                    .setData("Decode error " + ex.getMessage()));
            return;
        }

        String[] parts = credentials.split(":", 2);

        if (parts.length != 2) {

            restService.sendResponse(res, restResponse.setStatus(422)
                    .setData("Format error spliting by ':' "));
            return;

        }

        User user = dataContext.getUserDao().autorize(parts[0], parts[1]);

        if (user == null) {
            restService.sendResponse(res, restResponse.setStatus(401)
                    .setData("Credentials rejected"));
            return;
        }




        restResponse.setCashTime(600).setStatus(200).setData(user);
        restService.sendResponse(res, restResponse);

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        restService.setCorsHeaders(resp);
    }

}
