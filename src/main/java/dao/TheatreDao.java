package dao;

import entity.impl.TheatreImpl;

import java.util.List;

public interface TheatreDao extends GenericCRUD<TheatreImpl, Long>{

    List<TheatreImpl> search(String searchQuery, String sort);
}
