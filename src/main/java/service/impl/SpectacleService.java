package service.impl;

import dao.SpectacleDao;
import dao.impl.SpectacleDaoImpl;
import dto.SpectacleDto;
import entity.impl.SpectacleImpl;
import service.ServiceGeneric;

import java.util.LinkedList;
import java.util.List;

public class SpectacleService {
    private static SpectacleDao dao = SpectacleDaoImpl.getInstance();
    private static SpectacleService service;

    private SpectacleService() {}
    public static SpectacleService getInstance(){
        if(service == null)
            service = new SpectacleService();
        return service;
    }

    public SpectacleImpl transformToEntity(SpectacleDto dto) {
        SpectacleImpl entity = new SpectacleImpl();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSummary(dto.getSummary());
        return entity;
    }

    public SpectacleDto transformToDto(SpectacleImpl entity) {
        SpectacleDto dto = new SpectacleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSummary(entity.getSummary());
        dto.setCategory(entity.getCategory());
        return dto;
    }

    public void create(SpectacleDto dto){dao.create(transformToEntity(dto));}
    public SpectacleDto read(Long key){return transformToDto(dao.read(key));}
    public void update(SpectacleDto dto){dao.update(transformToEntity(dto));}
    public void delete(Long key){
        dao.delete(key);
    }

    public List<SpectacleDto> getAll() {
        List<SpectacleImpl> entity = dao.getAll();
        List<SpectacleDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }

    public List<SpectacleDto> search(String searchQuery, String sort) {
        List<SpectacleImpl> entity = dao.search(searchQuery, sort);
        List<SpectacleDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }
}
