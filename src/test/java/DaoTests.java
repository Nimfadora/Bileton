import dao.AudienceDao;
import dao.SpectacleDao;
import dao.TroupeDao;
import dao.impl.AudienceDaoImpl;
import dao.impl.SpectacleDaoImpl;
import dao.impl.TroupeDaoImpl;
import dao.impl.report.Automatization;
import dao.impl.report.PlayReportDao;
import dao.impl.report.TicketDao;
import dto.*;
import entity.impl.PlaceImpl;
import entity.impl.PlayReportImpl;
import entity.impl.PlaysInTheatresImpl;
import entity.impl.TicketImpl;
import org.junit.Test;
import service.impl.*;

import java.util.List;

public class DaoTests {
    @Test
    public void PlayReportTest(){
        PlayReportDao dao = PlayReportDao.getInstance();
        List<PlayReportImpl> plays = dao.getAll();
    }

    @Test
    public void troupeServiceTest(){
        TroupeService service = TroupeService.getInstance();
        List<TroupeDto> troupes =  service.getAll();
    }
    @Test
    public void spectacleServiceTest(){
        SpectacleService service = SpectacleService.getInstance();
        List<SpectacleDto> spectacles =  service.getAll();
    }
    @Test
    public void performanceServiceTest(){
        PerformanceService service = PerformanceService.getInstance();
        List<PerformanceDto> spectacles =  service.getAll();
    }
    @Test
    public void theatreGetAllTest(){
        TheatreService service = TheatreService.getInstance();
        List<TheatreDto> spectacles =  service.getAll();
        spectacles.forEach(System.out::println);
    }
    @Test
    public void editorTest(){
        EditorService service = EditorService.getInstance();
        String res = service.performQuery("Select * from theatre");
        System.out.println(res);
    }
    @Test
    public void pitTest(){
        PlayService service = PlayService.getInstance();
        List<PlaysInTheatresImpl> lst = service.getPlaysInTheatres();
        lst.forEach(System.out::println);
    }
    @Test
    public void autoTest(){
        Automatization dao = Automatization.getInstance();
        dao.fillAudience((long) 5);
    }
    @Test
    public void ticketTest(){
        TicketDao dao = TicketDao.getInstance();
        Long [] ids = {(long)11, (long)12};
        dao.buyTicket(ids).forEach((System.out::println));
    }

    @Test
    public void playReportTest(){
        PlayReportService service = PlayReportService.getInstance();
        List<PlayReportDto> report = service.getFiltered(null, null, null, null, "2", "2");
        report.forEach(System.out::println);
    }

    @Test
    public void audGetAllTest(){
        AudienceDao dao  = AudienceDaoImpl.getInstance();
        List<PlaceImpl> audience = dao.getAll((long) 2);
        audience.forEach(System.out::println);
    }

    @Test
    public void ticketStatisticsTest(){
        TicketDao dao = TicketDao.getInstance();
        dao.getTicketStatistics().forEach(System.out::println);
    }

    @Test
    public void troupeStatisticsTest(){
        TroupeDaoImpl dao = TroupeDaoImpl.getInstance();
        dao.getTroupesStatistics().forEach(System.out::println);
    }



    @Test
    public void spectacleSearchTest(){
        SpectacleService service = SpectacleService.getInstance();
        service.search("НАЧАТЬ СНАЧАЛА", "DESC");
    }

}
