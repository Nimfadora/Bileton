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
import java.util.List;

@WebServlet({"/admin/ticket/buy/*"})
public class UserTicketController extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tickets = request.getParameter("tickets");
        String [] arr = tickets.split(",");
        String outputId = "";
        for(String id : arr){
            File file = new File("C:\\Users\\Владелец\\IdeaProjects\\Bileton\\src\\main\\webapp\\reportTemplates\\"+id+".html");
            System.out.println("Html file with id = " + id+" delete is "+file.delete());
            outputId += id;
        }
        File file = new File("C:\\Users\\Владелец\\IdeaProjects\\Bileton\\src\\main\\webapp\\reportTemplates\\"+outputId+".pdf");
        System.out.println("Pdf file with id = "+ outputId +" delete is "+file.delete());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = Params.EMPTYSTRING;
        InputStream input = req.getInputStream();
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
            String outputId = "";
            for (Long id : ticketReq.getPlacesId()) {
                outputId += id.toString();
            }
            ToPdfConverter.createPdf(HtmlTemplateRenderer.render(tickets), outputId);
            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();
            out.write(outputId);
        }
        else
            TicketService.getInstance().reserve(ticketReq.getPlacesId());
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = "";
        InputStream input = req.getInputStream();
        BufferedInputStream in =
                new BufferedInputStream(input);
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
        TicketDto ticket = mapper.readValue(json, TicketDto.class);
        String outputId = "";
        for(Long id : ticket.getPlacesId()){
            File file = new File("C:\\Users\\Владелец\\IdeaProjects\\Bileton\\src\\main\\webapp\\reportTemplates\\"+id+".html");
            System.out.println("Html file delete is "+file.delete());
            outputId += id.toString();
        }
        File file = new File("C:\\Users\\Владелец\\IdeaProjects\\Bileton\\src\\main\\webapp\\reportTemplates\\"+outputId+".pdf");
        System.out.println("Pdf file delete is "+file.delete());

    }
}
