package controllers.fetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.SpectacleDto;
import service.impl.SpectacleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@WebServlet({"/admin/spectacle/fetch/*"})
public class SpectacleController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");

        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json");

        if(search!=null && !search.equals("") || sort!=null && !sort.equals("")) {
            mapper.writeValue(response.getOutputStream(), SpectacleService.getInstance().search(URLDecoder.decode(search, "UTF-8"), sort));
        }else
            mapper.writeValue(response.getOutputStream(), SpectacleService.getInstance().getAll());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        SpectacleDto spectacle = mapper.readValue(json, SpectacleDto.class);
        SpectacleService.getInstance().create(spectacle);

        req.getRequestDispatcher("/pages/spectacle.jsp").forward(req, resp);
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

        SpectacleDto spectacle = mapper.readValue(json, SpectacleDto.class);
        System.out.println(spectacle);
        SpectacleService.getInstance().update(spectacle);

        req.getRequestDispatcher("/pages/spectacle.jsp").forward(req, resp);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getRequestURI();
        String[] parts = json.split("/");
        Long res = Long.valueOf(parts[parts.length-1]);
        SpectacleService.getInstance().delete(res);

        req.getRequestDispatcher("/pages/spectacl.jsp").forward(req, resp);
    }
}
