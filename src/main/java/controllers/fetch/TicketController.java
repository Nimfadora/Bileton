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

    private ObjectMapper mapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream input = request.getInputStream();
        BufferedInputStream in = new BufferedInputStream(input);
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(in));
        TicketDto ticketReq = mapper.readValue(reader, TicketDto.class);
        List<TicketImpl> tickets;
        if(ticketReq.getReserveType() == 1) {
            tickets = TicketService.getInstance().buyTicket(ticketReq.getPlacesId());
            ToPdfConverter.createPdf(HtmlTemplateRenderer.render(tickets), "123123");
        }
        else
            TicketService.getInstance().reserve(ticketReq.getPlacesId());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
