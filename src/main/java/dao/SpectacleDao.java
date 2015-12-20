package dao;

import entity.impl.SpectacleImpl;

import java.util.List;

public interface SpectacleDao extends GenericCRUD<SpectacleImpl, Long> {
    List<SpectacleImpl> search(String searchQuery, String sort);
}
