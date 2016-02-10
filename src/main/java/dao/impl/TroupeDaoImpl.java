package dao.impl;

import dao.TroupeDao;
import entity.impl.TroupeImpl;
import entity.impl.TroupeStatisticsImpl;
import util.DbUtil;
import service.ConnectionFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TroupeDaoImpl implements TroupeDao {

    private Connection connection;
    private PreparedStatement statement;
    private static TroupeDaoImpl dao;

    private TroupeDaoImpl() {}

    public static TroupeDaoImpl getInstance(){
        if(dao == null)
            dao = new TroupeDaoImpl();
        return dao;
    }


    public static final String CREATE = "INSERT troupe SET name= ? ;";
    public static final String READ = "SELECT * FROM troupe WHERE id= ? ;";
    public static final String UPDATE = "UPDATE troupe SET name= ? WHERE id= ? ;";
    public static final String DELETE = "DELETE FROM troupe WHERE id= ? ;";
    public static final String GET_ALL = "SELECT * FROM troupe;";

    public static final String GET_STATISTICS = "SELECT troupe.name, Count(actor.actor_id) " +
            "FROM troupe, actor " +
            "WHERE actor.troupe_id = troupe.id " +
            "GROUP BY troupe.id, troupe.name ;";


    public static final String SEARCH_ALL_THE_REST = "SELECT * FROM troupe WHERE UPPER(troupe.name) != ? ;";
    public static final String SEARCH = "SELECT * FROM troupe WHERE UPPER(troupe.name) = ?;";

    public static final String SORT_ASC = "SELECT * FROM troupe ORDER BY troupe.name ASC;";
    public static final String SORT_DESC = "SELECT * FROM troupe ORDER BY troupe.name DESC;";

    public static final String SEARCH_ALL_THE_REST_SORT_ASC = "SELECT * FROM troupe WHERE UPPER(troupe.name) != ? ORDER BY troupe.name ASC;";
    public static final String SEARCH_ALL_THE_REST_SORT_DESC = "SELECT * FROM troupe WHERE UPPER(troupe.name) != ? ORDER BY troupe.name DESC;";

    @Override
    public void create(TroupeImpl troupe) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);
            statement.setString(1, troupe.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public TroupeImpl read(Long key) {
        ResultSet res;
        TroupeImpl troupe = new TroupeImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(READ);
            statement.setLong(1, key);
            res = statement.executeQuery();
            while (res.next()) {
                troupe.setId(res.getLong(1));
                troupe.setName(res.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return troupe;
    }

    @Override
    public void update(TroupeImpl troupe) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, troupe.getName());
            statement.setLong(2, troupe.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public void delete(Long key) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setLong(1, key);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public List<TroupeImpl> getAll() {
        ResultSet rs = null;
        List<TroupeImpl> troupes = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            rs = statement.executeQuery();
            while (rs.next()){
                TroupeImpl troupe = new TroupeImpl();
                troupe.setId(rs.getLong(1));
                troupe.setName(rs.getString(2));
                troupes.add(troupe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return troupes;
    }


    public List getTroupesStatistics(){
        ResultSet rs = null;
        List<TroupeStatisticsImpl> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_STATISTICS);
            rs = statement.executeQuery();
            while (rs.next()) {
                TroupeStatisticsImpl statistics = new TroupeStatisticsImpl();
                statistics.setTroupe(rs.getString(1));
                statistics.setCountActors(rs.getInt(2));
                lst.add(statistics);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return lst;
    }

    @Override
    public List<TroupeImpl> search(String searchQuery, String sort) {
        ResultSet rs = null;
        PreparedStatement searchResult = null;
        PreparedStatement allOtherResults;
        List<TroupeImpl> troupes = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            if(searchQuery!=null && !searchQuery.equals("")) {
                searchResult = connection.prepareStatement(SEARCH);
                searchResult.setString(1, searchQuery);
                if (sort != null && !sort.equals("")) {
                    if(sort.equals("ASC"))
                        allOtherResults = connection.prepareStatement(SEARCH_ALL_THE_REST_SORT_ASC);
                    else
                        allOtherResults = connection.prepareStatement(SEARCH_ALL_THE_REST_SORT_DESC);
                }else {
                    allOtherResults = connection.prepareStatement(SEARCH_ALL_THE_REST);
                }
                allOtherResults.setString(1, searchQuery);
            }else{
                if(sort.equals("ASC"))
                    allOtherResults = connection.prepareStatement(SORT_ASC);
                else
                    allOtherResults = connection.prepareStatement(SORT_DESC);
            }

            if(searchResult!=null) {
                rs = searchResult.executeQuery();
                while (rs.next()) {
                    TroupeImpl troupe = new TroupeImpl();
                    troupe.setId(rs.getLong(1));
                    troupe.setName(rs.getString(2));
                    troupe.setCategory("selected");
                    troupes.add(troupe);
                }
            }
            rs = allOtherResults.executeQuery();
            while (rs.next()){
                TroupeImpl troupe = new TroupeImpl();
                troupe.setId(rs.getLong(1));
                troupe.setName(rs.getString(2));
                troupes.add(troupe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return troupes;
    }
}
