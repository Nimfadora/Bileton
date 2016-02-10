package controllers.fetch;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet({"/ticket/pdf/*"})
public class getPdfController extends HttpServlet {
    private static final String PATH = "src/main/resources/reportTemplates/";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String requestURI = request.getRequestURI();
//        System.out.println(requestURI);
//        String[] parts = requestURI.split("/");
//        Long id = Long.valueOf(parts[parts.length-1]);
//        byte[] outputBytes = new File();
//
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-control", "private");
//        response.setDateHeader("Expires", 0);
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"test.pdf\"");
//
//        if (outputBytes != null) {
//            response.setContentLength(outputBytes.length);
//            ServletOutputStream out = response.getOutputStream();
//            out.write(outputBytes);
//            out.flush();
//            out.close();
//        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }


}
