package controllers.fetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AudienceDto;
import dto.PlaceDto;
import service.impl.AudienceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/admin/audience/fetch/*"})
public class AudienceController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long playId = Long.parseLong(request.getParameter("play_id"));
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json");

        List<PlaceDto> allPlaces = AudienceService.getInstance().getAllPlaces(playId);
        mapper.writeValue(response.getOutputStream(), allPlaces);
    }
}
