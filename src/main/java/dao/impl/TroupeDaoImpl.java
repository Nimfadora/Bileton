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
    private Statement statement;
    private static TroupeDaoImpl dao;

    private TroupeDaoImpl() {}

    public static TroupeDaoImpl getInstance(){
        if(dao == null)
            dao = new TroupeDaoImpl();
        return dao;
    }

    @Override
    public void create(TroupeImpl troupe) {
        String query = "INSERT troupe SET name='"+troupe.getName()+"';";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public TroupeImpl read(Long key) {
        String query = "SELECT * FROM troupe WHERE id='"+key+"';";
        ResultSet res;
        TroupeImpl troupe = new TroupeImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            res = statement.executeQuery(query);
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
        String query = "UPDATE troupe SET name='"+troupe.getName()+"'WHERE id='"+troupe.getId()+"';";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public void delete(Long key) {
        String query = "DELETE FROM troupe WHERE id='"+key+"';";//возможно нужно каскадное уаление
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public List<TroupeImpl> getAll() {
        String query = "SELECT * FROM troupe";
        ResultSet rs = null;
        List<TroupeImpl> troupes = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
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
        String query = "SELECT troupe.name, Count(actor.actor_id) \n" +
                "FROM troupe, actor \n" +
                "WHERE actor.troupe_id = troupe.id \n" +
                "GROUP BY troupe.id, troupe.name ;";
        ResultSet rs = null;
        List<TroupeStatisticsImpl> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
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

    public static final String SEARCH_ALL_THE_REST = "SELECT * FROM troupe WHERE UPPER(troupe.name) != ? ;";
    public static final String SEARCH = "SELECT * FROM troupe WHERE UPPER(troupe.name) = ?;";

    public static final String SORT_ASC = "SELECT * FROM troupe ORDER BY troupe.name ASC;";
    public static final String SORT_DESC = "SELECT * FROM troupe ORDER BY troupe.name DESC;";

    public static final String SEARCH_ALL_THE_REST_SORT_ASC = "SELECT * FROM troupe WHERE UPPER(troupe.name) != ? ORDER BY troupe.name ASC;";
    public static final String SEARCH_ALL_THE_REST_SORT_DESC = "SELECT * FROM troupe WHERE UPPER(troupe.name) != ? ORDER BY troupe.name DESC;";

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
