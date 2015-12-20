package dao;

import entity.impl.PlaceImpl;

import java.util.List;

public interface AudienceDao extends GenericCRUD<PlaceImpl, Long> {
    List<PlaceImpl> getAll(Long playId);
}
