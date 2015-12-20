package service.impl;

import dao.AudienceDao;
import dao.impl.AudienceDaoImpl;
import dto.PlaceDto;
import entity.impl.PlaceImpl;
import entity.impl.PlayImpl;
import service.ServiceGeneric;

import java.util.LinkedList;
import java.util.List;

public class AudienceService{
    private static AudienceDao dao = AudienceDaoImpl.getInstance();
    private static AudienceService service;

    private AudienceService(){}

    public static AudienceService getInstance(){
        if(service == null)
            service = new AudienceService();
        return service;
    }

    public PlaceImpl transformToEntity(PlaceDto dto) {
        PlaceImpl entity = new PlaceImpl();
        entity.setId(dto.getId());
        entity.setAudienceId(dto.getAudienceId());
        entity.setPlaceNum(dto.getPlaceNum());
        entity.setRowNum(dto.getRowNum());
        entity.setState(dto.getState());
        entity.setCategory(dto.getCategory());

        return entity;
    }

    public PlaceDto transformToDto(PlaceImpl entity) {
        PlaceDto dto = new PlaceDto();
        dto.setId(entity.getId());
        dto.setAudienceId(entity.getAudienceId());
        dto.setPlaceNum(entity.getPlaceNum());
        dto.setRowNum(entity.getRowNum());
        dto.setState(entity.getState());
        dto.setCategory(entity.getCategory());

        return dto;
    }

    public List<PlaceDto> getAllPlaces(Long playId){
        List<PlaceImpl> entity = dao.getAll(playId);
        List<PlaceDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }
}
