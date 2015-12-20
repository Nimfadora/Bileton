package service.impl;

import dao.TroupeDao;
import dao.impl.TroupeDaoImpl;
import dto.TroupeDto;
import entity.impl.TroupeImpl;
import entity.impl.TroupeStatisticsImpl;
import service.ServiceGeneric;

import java.util.LinkedList;
import java.util.List;

public class TroupeService extends ServiceGeneric<TroupeDto, TroupeImpl, Long> {
    private static TroupeDao dao = TroupeDaoImpl.getInstance();
    private static TroupeService service;

    private TroupeService() {}

    public static TroupeService getInstance(){
        if(service == null)
            service = new TroupeService();
        return service;
    }

    public TroupeImpl transformToEntity(TroupeDto dto) {
        TroupeImpl entity = new TroupeImpl();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public TroupeDto transformToDto(TroupeImpl entity) {
        TroupeDto dto = new TroupeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        return dto;
    }

    public void create(TroupeDto dto){dao.create(transformToEntity(dto));}
    public TroupeDto read(Long key){return transformToDto(dao.read(key));}
    public void update(TroupeDto dto){dao.update(transformToEntity(dto));}
    public void delete(Long key){
        dao.delete(key);
    }
    public List<TroupeDto> getAll() {
        List<TroupeImpl> entity = dao.getAll();
        List<TroupeDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }

    public List<TroupeStatisticsImpl> getTroupeStatistics(){
        return dao.getTroupesStatistics();
    }

    public List<TroupeDto> search(String searchQuery, String sort){
        List<TroupeImpl> entity = dao.search(searchQuery, sort);
        List<TroupeDto> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }
}
