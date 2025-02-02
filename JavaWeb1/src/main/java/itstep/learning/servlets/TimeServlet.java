package itstep.learning.servlets;

import com.google.gson.Gson;
import itstep.learning.rest.RestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Timestamp time = new Timestamp(System.currentTimeMillis());
        DateTimeFormatter.ISO_DATE_TIME.format(time.toLocalDateTime());
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(time.toLocalDateTime());

        try {
            response.getWriter().print(
                    gson.toJson(new RestResponse().setStatus(200).setMessage(
                            time.toString() + " / " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                    .format(time.toLocalDateTime()))));
        } catch (IOException ex) {

            System.err.println(ex);

        }

    }
}
