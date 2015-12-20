package controllers.fetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TicketDto;
import entity.impl.TicketImpl;
import service.impl.TicketService;
import util.HtmlTemplateRenderer;
import util.Params;
import util.ToPdfConverter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@WebServlet({"/admin/ticket"})
public class TicketController extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = Params.EMPTYSTRING;
        InputStream input = request.getInputStream();
        BufferedInputStream in = new BufferedInputStream(input);
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(in));
        int i;
        while ((i = reader.read()) != -1)
        {
            json += (char) i;
        }
        in.close();
        ObjectMapper mapper = new ObjectMapper();

        TicketDto ticketReq = mapper.readValue(json, TicketDto.class);
        List<TicketImpl> tickets = null;
        if(ticketReq.getReserveType() == 1) {
            tickets = TicketService.getInstance().buyTicket(ticketReq.getPlacesId());
            ToPdfConverter.createPdf(HtmlTemplateRenderer.render(tickets), "123123");
        }
        else
            TicketService.getInstance().reserve(ticketReq.getPlacesId());



//        request.setAttribute("Ticket", ticket);
//        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/ticket.jsp");
//        rd.forward(request, response);

//        PlayReportService service = new PlayReportService();
//        ObjectMapper mapper = new ObjectMapper();
//
//        response.setContentType("application/json");
//        mapper.writeValue(response.getOutputStream(), service.getAll());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
