package service.impl;

import dao.impl.report.TicketDao;
import dto.TicketReserve;
import entity.impl.TicketImpl;
import entity.impl.TicketStatisticsImpl;

import java.util.LinkedList;
import java.util.List;

public class TicketService {
    private static TicketDao dao = TicketDao.getInstance();
    private static TicketService service;

    private TicketService(){}
    public static TicketService getInstance(){
        if(service == null)
            service = new TicketService();
        return service;
    }

    public List<TicketImpl> buyTicket(Long[] ids) {
        return dao.buyTicket(ids);
    }
    public List<TicketStatisticsImpl> getTicketStatistics(){
        return dao.getTicketStatistics();
    }

    public void reserve(Long[] ids) {
        dao.reserveTicket(ids);
    }
}
