package com.backend.servlets;

import java.io.IOException;
import java.util.Map;

import com.backend.rest.RestResponse;
import com.backend.rest.RestService;
import com.backend.services.random.RandomService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class RandomServlet extends HttpServlet {
    private final RandomService randomService;
    private final RestService restService;

    @Inject
    public RandomServlet(RandomService randomService, RestService restService) {
        this.randomService = randomService;
        this.restService = restService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message;

        String typeParam = req.getParameter("type");
        String lengthParam = req.getParameter("length");
        RestResponse restResponse = new RestResponse()
                .setResourceUrl("GET /random")
                .setMeta(Map.of(
                        "DataType", "object",
                        "read", "GET /random",
                        "type", "type param",
                        "length", "length param"));
        if (typeParam.equals("salt")) {

            try {

                int length = Integer.parseInt(lengthParam);

                message = randomService.noRestrictionsStr(length);
                restService.sendResponse(resp, restResponse
                        .setStatus(200)
                        .setResourceUrl("Get /random")
                        .setMeta(Map.of(
                                "DataType", "object",
                                "read", "GET /random",
                                "type", typeParam,
                                "length", lengthParam))
                        .setData(message));

            } catch (NumberFormatException ex) {

                restService.sendResponse(resp, restResponse.setStatus(400)
                        .setData("Bad Request"));
                return;
            }

        } else if (typeParam.equals("fileName")) {

            try {

                int length = Integer.parseInt(lengthParam);

                message = randomService.fileNameRandomStr(length);
                restService.sendResponse(resp, restResponse
                        .setStatus(200)
                        .setResourceUrl("Get /random")
                        .setMeta(Map.of(
                                "DataType", "object",
                                "read", "GET /random",
                                "type", typeParam,
                                "length", lengthParam))
                        .setData(message));

            } catch (NumberFormatException ex) {

                restService.sendResponse(resp, restResponse.setStatus(400)
                        .setData("Bad Request"));
                return;
            }
        } else {

            restService.sendResponse(resp, restResponse.setStatus(400)
                    .setData("Bad Request"));
        }

    }

}
