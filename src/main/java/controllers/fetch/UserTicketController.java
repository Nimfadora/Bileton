package controllers.fetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TicketDto;
import entity.impl.TicketImpl;
import service.impl.MailService;
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
import java.util.Arrays;
import java.util.List;

@WebServlet({"/admin/ticket/buy/*"})
public class UserTicketController extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        TicketDto ticketReq = mapper.readValue(req.getInputStream(), TicketDto.class);

        List<TicketImpl> tickets = null;
        if(ticketReq.getReserveType() == 1) {
            tickets = TicketService.getInstance().buyTicket(ticketReq.getPlacesId());
            String outputId = "";
            for (Long id : ticketReq.getPlacesId()) {
                outputId += id.toString();
            }

            ToPdfConverter.createPdf(HtmlTemplateRenderer.render(tickets), outputId);

            final String userEmail = ticketReq.getEmail();
            final String pdfFileName = outputId + ".pdf";
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
                    MailService.getInstance().sendMailTo(userEmail, pdfFileName, ticketReq.getReserveType());
//                }
//            });

            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();
            out.write(outputId);
        }
        else {
            TicketService.getInstance().reserve(ticketReq.getPlacesId());
            String id = "";
            for (Long ticketId : ticketReq.getPlacesId()) {
                id += ticketId.toString();
            }
            final String orderId = id;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
            MailService.getInstance().sendMailTo(ticketReq.getEmail(), orderId, ticketReq.getReserveType());
//                }
//            });
        }
    }
}
