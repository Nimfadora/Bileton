package service.impl;

import dao.TheatreDao;
import dao.impl.TheatreDaoImpl;
import dto.TheatreDto;
import entity.impl.TheatreImpl;
import service.ServiceGeneric;

import java.util.LinkedList;
import java.util.List;

public class TheatreService{
    private static TheatreDao dao = TheatreDaoImpl.getInstance();
    private static TheatreService service;

    private TheatreService() {}

    public static TheatreService getInstance(){
        if(service == null)
            service = new TheatreService();
        return service;
    }

    public TheatreImpl transformToEntity(TheatreDto dto) {
        TheatreImpl entity = new TheatreImpl();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public TheatreDto transformToDto(TheatreImpl entity) {
        TheatreDto dto = new TheatreDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        return dto;
    }

    public void create(TheatreDto dto){dao.create(transformToEntity(dto));}
    public TheatreDto read(Long key){return transformToDto(dao.read(key));}
    public void update(TheatreDto dto){dao.update(transformToEntity(dto));}
    public void delete(Long key){
        dao.delete(key);
    }

    public List<TheatreDto> getAll() {
        List<TheatreImpl> entity = dao.getAll();
        List<TheatreDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }

    public List<TheatreDto> search(String searchQuery, String sort){
        List<TheatreImpl> entity = dao.search(searchQuery, sort);
        List<TheatreDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }
}
