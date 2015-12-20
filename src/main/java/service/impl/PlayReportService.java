package service.impl;

import dao.impl.report.PlayReportDao;
import dto.PlayReportDto;
import entity.impl.PlayReportImpl;

import java.util.LinkedList;
import java.util.List;

public class PlayReportService {
    private static PlayReportDao dao = PlayReportDao.getInstance();
    private static PlayReportService service;

    private PlayReportService(){}

    public static PlayReportService getInstance(){
        if(service == null)
            service = new PlayReportService();
        return service;
    }

    public PlayReportDto transformToDto(PlayReportImpl entity) {
        PlayReportDto report = new PlayReportDto();
        report.setId(entity.getId());
        report.setName(entity.getName());
        report.setTheatre(entity.getTheatre());
        report.setTroupe(entity.getTroupe());
        report.setDate(entity.getDate());
        report.setTime(entity.getTime());
        report.setSummary(entity.getSummary());
        report.setStarring(entity.getStarring());
        report.setPrices(entity.getPrices());
        return report;
    }

    public List<PlayReportDto> getAll() {
        List<PlayReportImpl> entity = dao.getAll();
        List<PlayReportDto> dto = new LinkedList<>();
        entity.forEach(spectacle -> {
            dto.add(transformToDto(spectacle));
        });
        return dto;
    }
    public List<PlayReportDto> getFiltered(String search, String theatre, String dateFrom, String dateTo, String period, String sort){

        List<PlayReportImpl> entity = dao.getFiltered(search, theatre, convertDate(dateFrom), convertDate(dateTo), period, sort);
        List<PlayReportDto> dto = new LinkedList<>();
        entity.forEach(spectacle -> {
            dto.add(transformToDto(spectacle));
        });
        return dto;
    }

    private String convertDate(String date){
        if(date!=null && !date.equals("")) {
            String[] arr = date.split("/");
            return arr[2] + "-" + arr[0] + "-" + arr[1];
        }
        return date;
    }

}
