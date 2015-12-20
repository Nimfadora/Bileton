package service.impl;

import dao.PerformanceDao;
import dao.impl.PerformanceDaoImpl;
import dto.PerformanceDto;
import entity.impl.PerformanceImpl;
import service.ServiceGeneric;

import java.util.LinkedList;
import java.util.List;

public class PerformanceService {
    private static PerformanceDao dao = PerformanceDaoImpl.getInstance();;
    private static PerformanceService service;

    private PerformanceService() {}

    public static PerformanceService getInstance(){
        if(service == null)
            service = new PerformanceService();
        return service;
    }

    public void create(PerformanceDto dto){dao.create(transformToEntity(dto));}

    public PerformanceDto read(Long key){return transformToDto(dao.read(key));}

    public void update(PerformanceDto dto){dao.update(transformToEntity(dto));}

    public void delete(Long key){
        dao.delete(key);
    }

    public List<PerformanceDto> getAll(){
        List<PerformanceImpl> entity = dao.getAll();
        List<PerformanceDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(specialTransformToDto(ent));
        });
        return dto;
    }

    public PerformanceImpl transformToEntity(PerformanceDto dto) {
        PerformanceImpl entity = new PerformanceImpl();
        entity.setId(dto.getId());
        entity.setSpectacle_id(dto.getSpectacle_id());
        entity.setTroupe_id(dto.getTroupe_id());
        return entity;
    }


    public PerformanceDto transformToDto(PerformanceImpl entity) {
        PerformanceDto dto = new PerformanceDto();
        dto.setId(entity.getId());
        dto.setSpectacle_id(entity.getSpectacle_id());
        dto.setTroupe_id(entity.getTroupe_id());
        return dto;
    }

    private PerformanceDto specialTransformToDto(PerformanceImpl entity) {
        PerformanceDto dto = new PerformanceDto();
        dto.setId(entity.getId());
        dto.setSpectacle(entity.getSpectacle());
        dto.setTroupe(entity.getTroupe());
        dto.setSpectacle_id(entity.getSpectacle_id());
        dto.setTroupe_id(entity.getTroupe_id());
        return dto;
    }
}
