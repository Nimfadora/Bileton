package service;

import dao.GenericCRUD;

import java.util.LinkedList;
import java.util.List;

public abstract class ServiceGeneric <T, E, K> {
    public GenericCRUD<E, K> dao;

    public abstract E transformToEntity(T dto);
    public abstract T transformToDto(E entity);

    public void create(T dto){this.dao.create(transformToEntity(dto));}
    public T read(K key){return transformToDto(this.dao.read(key));}
    public void update(T dto){this.dao.update(transformToEntity(dto));}
    public void delete(K key){
        this.dao.delete(key);
    }
    public List<T> getAll() {
        List<E> entity = this.dao.getAll();
        List<T> dto = new LinkedList<>();
        entity.forEach(ent -> {
            dto.add(transformToDto(ent));
        });
        return dto;
    }
}
