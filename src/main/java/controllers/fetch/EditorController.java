package controllers.fetch;


import service.impl.EditorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet({"/admin/editor"})
public class EditorController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query =  new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
        System.out.println(query);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(EditorService.getInstance().performQuery(query));
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

        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(EditorService.getInstance().performQuery(json));
    }

}
