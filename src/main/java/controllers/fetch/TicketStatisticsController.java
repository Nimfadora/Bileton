package controllers.fetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.impl.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/admin/ticketSt/fetch"})
public class TicketStatisticsController extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), TicketService.getInstance().getTicketStatistics());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
