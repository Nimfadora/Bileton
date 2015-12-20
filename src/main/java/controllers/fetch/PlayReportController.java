package controllers.fetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PlayReportDto;
import service.impl.PlayReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@WebServlet({"/admin/playReport/fetch"})
public class PlayReportController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String theatre = request.getParameter("theatre");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        String period = request.getParameter("period");
        String sort= request.getParameter("sort");
        System.out.println("search: "+ search +
                "\ntheatre: "+ theatre +
                "\nfrom: "+ dateFrom +
                "\nto: "+ dateTo +
                "\nperiod: "+ period +
                "\nsort: "+ sort);
        List<PlayReportDto> resultSet;
        ObjectMapper mapper = new ObjectMapper();

        if((search == null && theatre == null && dateFrom == null && dateTo == null && period == null && sort == null) || (search.equals("") && theatre == null && dateFrom.equals("") && dateTo.equals("") && period.equals("3") && sort == null))
            resultSet = PlayReportService.getInstance().getAll();
        else
            resultSet = PlayReportService.getInstance().getFiltered(URLDecoder.decode(search, "UTF-8"), theatre, dateFrom, dateTo, period, sort);

        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), resultSet);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
