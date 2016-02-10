package dao;

import entity.impl.PerformanceImpl;

import java.util.List;

public interface PerformanceDao extends GenericCRUD<PerformanceImpl, Long> {
    public List<PerformanceImpl> search(String searchQuery, String sort);

}
