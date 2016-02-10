package controllers.fetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PerformanceDto;
import service.impl.PerformanceService;
import util.Params;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;

@WebServlet({"/admin/performance/fetch/*"})
public class PerfController extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");

        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json");

        if(search!=null && !search.equals("") || sort!=null && !sort.equals("")) {
            System.out.println(search + " "+ sort);
            mapper.writeValue(response.getOutputStream(), PerformanceService.getInstance().search(URLDecoder.decode(search, "UTF-8"), sort));
        }else
            mapper.writeValue(response.getOutputStream(), PerformanceService.getInstance().getAll());
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

        PerformanceDto perf = mapper.readValue(json, PerformanceDto.class);
        PerformanceService.getInstance().create(perf);

        //TODO: understend the purpose of the next line
//        req.getRequestDispatcher("/pages/performance.jsp").forward(req, resp);
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException{
        String json = Params.EMPTYSTRING;
        InputStream input = req.getInputStream();
        //passes null value TODO: fix it
        //TODO: fix selection if search failed
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

        PerformanceDto perf = mapper.readValue(json, PerformanceDto.class);
        System.out.println(perf);
        PerformanceService.getInstance().update(perf);

//        req.getRequestDispatcher("/pages/performance.jsp").forward(req, resp);
    }
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException{
        String json = req.getRequestURI();
        String[] parts = json.split("/");
        Long res = Long.valueOf(parts[parts.length-1]);
        PerformanceService.getInstance().delete(res);

//        req.getRequestDispatcher("/pages/performance.jsp").forward(req, resp);
    }
}
