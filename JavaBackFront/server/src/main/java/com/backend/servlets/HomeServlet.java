package com.backend.servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.backend.models.UserSignUpFormModel;
import com.backend.rest.RestResponse;
import com.backend.services.random.RandomService;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

//@WebServlet("/home")
@Singleton
public class HomeServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final RandomService randomService;
    @Inject
    public HomeServlet(RandomService randomService){

        this.randomService=randomService;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String connectionString = "jdbc:mysql://localhost:3308/javaServlet";
            Connection connection = DriverManager.getConnection(connectionString, "userJavaServlet", "java");

            String query = "SELECT CURRENT_TIMESTAMP";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            message = resultSet.getString(1);
        } catch (SQLException e) {
            message = e.getMessage();
        }
        sendJson(resp, new RestResponse()
                .setResourceUrl("POST /home")
                .setStatus(200).setMessage(message +"  "+ randomService.randomInt()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = new String(req.getInputStream().readAllBytes());

        UserSignUpFormModel model;
        RestResponse restResponse=new RestResponse()
        .setResourceUrl("POST /home")
        .setCashTime(0);

        try {

         model= gson.fromJson(body, UserSignUpFormModel.class);

        } catch (Exception ex) {
            sendJson(resp, restResponse
            .setStatus(422)
            .setMessage(ex.getMessage())
            );
            return;
        }
        sendJson(resp, restResponse
                .setStatus(201)
                .setMessage("Create")
                .setMeta(Map.of(

                        "DataType", "object",
                        "read", "GET /home",
                        "update", "PUT /home",
                        "delete", "DELETE /home"))
                .setData(model));

    }

    // Setting cors
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-headers", "content-type");
    }

    /****/
    private void sendJson(HttpServletResponse resp, RestResponse restResponse) throws IOException {
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.getWriter().print(
                gson.toJson(restResponse));
    }

}
