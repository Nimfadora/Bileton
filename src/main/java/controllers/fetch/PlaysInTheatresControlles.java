package controllers.fetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.impl.PlayService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/admin/playsInTheatres/fetch"})
public class PlaysInTheatresControlles extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), PlayService.getInstance().getPlaysInTheatres());
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
