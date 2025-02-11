package com.backend.rest;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.google.gson.Gson;

public class RestService {
    private final Gson gson = new Gson();

    public void sendResponse(HttpServletResponse resp, RestResponse restResponse) throws IOException {
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.getWriter().print(
                gson.toJson(restResponse));
    }

    public void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-headers", "authorization,content-type");
    }

    public <T> T fromJson(String json, Class<T> classofT) {

        return gson.fromJson(json, classofT);
    }

}
