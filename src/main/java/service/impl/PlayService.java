package service.impl;

import dao.PlayDao;
import dao.impl.PlayDaoImpl;
import dto.PlayDto;
import entity.impl.PlayImpl;
import entity.impl.PlaysInTheatresImpl;
import service.ServiceGeneric;

import java.util.LinkedList;
import java.util.List;

public class PlayService {
    private static PlayDao dao = PlayDaoImpl.getInstance();
    private static PlayService service;

    private PlayService() {}

    public static PlayService getInstance(){
        if(service == null)
            service = new PlayService();
        return service;
    }

    public PlayImpl transformToEntity(PlayDto dto) {
        PlayImpl entity = new PlayImpl();
        entity.setPerformance_id(dto.getPerformance_id());
        entity.setTime(dto.getTime());
        entity.setDate(dto.getDate());
        entity.setId(dto.getId());
        return entity;
    }

    public PlayDto transformToDto(PlayImpl entity) {
        PlayDto dto = new PlayDto();
        dto.setPerformance_id(dto.getPerformance_id());
        dto.setTime(dto.getTime());
        dto.setDate(dto.getDate());
        dto.setId(dto.getId());
        return dto;
    }

    public void create(PlayDto dto){dao.create(transformToEntity(dto));}
    public PlayDto read(Long key){return transformToDto(dao.read(key));}
    public void update(PlayDto dto){dao.update(transformToEntity(dto));}
    public void delete(Long key){
        dao.delete(key);
    }
    public List<PlayDto> getAll() {
        List<PlayImpl> entity = dao.getAll();
        List<PlayDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }

    public List<PlaysInTheatresImpl> getPlaysInTheatres() {
        return dao.getPlaysInTheatres();
    }
}
