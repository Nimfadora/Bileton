package dao.impl;

import dao.PerformanceDao;
import entity.impl.PerformanceImpl;
import service.ConnectionFactory;
import util.DbUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PerformanceDaoImpl implements PerformanceDao {

    public static final String SEARCH_ALL_THE_REST = "SELECT performance.*, spectacle.name, troupe.name FROM performance, spectacle, troupe " +
            "WHERE performance.spectacle_id = spectacle.id AND performance.troupe_id = troupe.id AND UPPER(spectacle.name) != ?;";
    public static final String SEARCH = "SELECT performance.*, spectacle.name, troupe.name FROM performance, spectacle, troupe " +
            "WHERE performance.spectacle_id = spectacle.id AND performance.troupe_id = troupe.id AND UPPER(spectacle.name) = ?;";

    public static final String SORT_ASC = "SELECT performance.*, spectacle.name, troupe.name FROM performance, spectacle, troupe " +
            "WHERE performance.spectacle_id = spectacle.id AND performance.troupe_id = troupe.id ORDER BY spectacle.name ASC;";
    public static final String SORT_DESC = "SELECT performance.*, spectacle.name, troupe.name FROM performance, spectacle, troupe " +
            "WHERE performance.spectacle_id = spectacle.id AND performance.troupe_id = troupe.id ORDER BY spectacle.name DESC;";

    public static final String SEARCH_ALL_THE_REST_SORT_ASC = "SELECT performance.*, spectacle.name, troupe.name FROM performance, spectacle, troupe " +
            "WHERE performance.spectacle_id = spectacle.id AND performance.troupe_id = troupe.id AND UPPER(spectacle.name) != ? ORDER BY spectacle.name ASC;";
    public static final String SEARCH_ALL_THE_REST_SORT_DESC = "SELECT performance.*, spectacle.name, troupe.name FROM performance, spectacle, troupe " +
            "WHERE performance.spectacle_id = spectacle.id AND performance.troupe_id = troupe.id AND UPPER(spectacle.name) != ? ORDER BY spectacle.name DESC;";
    public static final String GET_ALL = "SELECT performance.*, spectacle.name, troupe.name FROM performance, spectacle, troupe WHERE performance.spectacle_id=spectacle.id AND performance.troupe_id=troupe.id;";

    public static final String CREATE = "INSERT performance SET spectacle_id = ?, troupe_id= ? ;";

    public static final String READ = "SELECT * FROM performance WHERE performance_id= ? ;";

    public static final String UPDATE = "UPDATE performance SET spectacle_id= ? , troupe_id= ? WHERE performance_id= ? ;";

    public static final String DELETE = "DELETE FROM performance WHERE performance_id= ? ;";

    //TODO: Concurrent updatable in result set
    private Connection connection;
    private PreparedStatement statement;
    private static PerformanceDao dao;

    private PerformanceDaoImpl() {}

    public static PerformanceDao getInstance(){
        if(dao == null)
            dao = new PerformanceDaoImpl();
        return dao;
    }

    @Override
    public List<PerformanceImpl> getAll() {
        ResultSet rs = null;
        List<PerformanceImpl> performances = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            rs = statement.executeQuery();
            while (rs.next()){
                PerformanceImpl perf = new PerformanceImpl();
                perf.setId(rs.getLong(1));
                perf.setSpectacle_id(rs.getLong(2));
                perf.setTroupe_id(rs.getLong(3));
                perf.setSpectacle(rs.getString(4));
                perf.setTroupe(rs.getString(5));

                performances.add(perf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return performances;
    }
    @Override
    public List<PerformanceImpl> search(String searchQuery, String sort) {
        ResultSet rs = null;
        PreparedStatement searchResult = null;
        PreparedStatement allOtherResults;
        List<PerformanceImpl> performances = new LinkedList<>();
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
                    PerformanceImpl perf = new PerformanceImpl();
                    perf.setId(rs.getLong(1));
                    perf.setSpectacle_id(rs.getLong(2));
                    perf.setTroupe_id(rs.getLong(3));
                    perf.setSpectacle(rs.getString(4));
                    perf.setTroupe(rs.getString(5));
                    perf.setCategory("selected");

                    performances.add(perf);
                }
            }
            rs = allOtherResults.executeQuery();
            while (rs.next()){
                PerformanceImpl perf = new PerformanceImpl();
                perf.setId(rs.getLong(1));
                perf.setSpectacle_id(rs.getLong(2));
                perf.setTroupe_id(rs.getLong(3));
                perf.setSpectacle(rs.getString(4));
                perf.setTroupe(rs.getString(5));

                performances.add(perf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return performances;
    }

    @Override
    public void create(PerformanceImpl entity) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);
            statement.setLong(1, entity.getSpectacle_id());
            statement.setLong(2, entity.getTroupe_id());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public PerformanceImpl read(Long key) {
        ResultSet rs = null;
        PerformanceImpl perf = new PerformanceImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(READ);
            statement.setLong(1, key);
            rs = statement.executeQuery();
            while (rs.next()){
                perf.setId(rs.getLong(1));
                perf.setSpectacle_id(rs.getLong(2));
                perf.setTroupe_id(rs.getLong(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return perf;
    }

    @Override
    public void update(PerformanceImpl entity) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setLong(1, entity.getSpectacle_id());
            statement.setLong(2, entity.getTroupe_id());
            statement.setLong(3, entity.getId());
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
}
