package itstep.learning.servlets;

import com.google.gson.Gson;
import itstep.learning.rest.RestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        String message;
        try {
            DriverManager.registerDriver(
                    new com.mysql.jdbc.Driver()
            );
            String connectionString = "jdbc:mysql://localhost:3308/javaServlet";
            Connection connection = DriverManager.getConnection(connectionString,"userJavaServlet","java");

            String query = "SELECT CURRENT_TIMESTAMP";
            //String queryOracle = "SELECT CURRENT_TIMESTAMP FROM dual";
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
             resultSet.next();
            message= resultSet.getString(1); //!!! JDBC countdown  1;
        } catch (SQLException e) {
            message=e.getMessage();
        }
        sendJson(resp,200,message);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body =new String(req.getInputStream().readAllBytes());
        sendJson(resp,201,body);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-headers", "content-type");
    }

    private void sendJson(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.getWriter().print(
                gson.toJson(new RestResponse().setStatus(status).setMessage(message))
        );
    }
}
