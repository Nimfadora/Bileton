package dao;

import entity.impl.TroupeImpl;
import entity.impl.TroupeStatisticsImpl;

import java.util.List;

public interface TroupeDao extends GenericCRUD<TroupeImpl, Long> {

    List<TroupeImpl> search(String searchQuery, String sort);

    List<TroupeStatisticsImpl> getTroupesStatistics();
}
