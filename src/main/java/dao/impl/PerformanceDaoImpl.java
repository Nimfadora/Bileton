package dao.impl;

import dao.PerformanceDao;
import entity.impl.PerformanceImpl;
import service.ConnectionFactory;
import util.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class PerformanceDaoImpl implements PerformanceDao {

    //TODO: Concurrent updatable in result set
    private Connection connection;
    private Statement statement;
    private static PerformanceDao dao;

    private PerformanceDaoImpl() {}

    public static PerformanceDao getInstance(){
        if(dao == null)
            dao = new PerformanceDaoImpl();
        return dao;
    }

    @Override
    public List<PerformanceImpl> getAll() {
        String query = "SELECT performance.*, spectacle.name, troupe.name FROM performance, spectacle, troupe WHERE performance.spectacle_id=spectacle.id AND performance.troupe_id=troupe.id;";
        ResultSet rs = null;
        List<PerformanceImpl> performances = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
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
        String query = "INSERT performance SET spectacle_id='"+entity.getSpectacle_id()+"', troupe_id='"+entity.getTroupe_id()+"' ;";
        ResultSet res;
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
    public PerformanceImpl read(Long key) {
        String query = "SELECT * FROM performance WHERE performance_id="+key+";";
        ResultSet rs = null;
        PerformanceImpl perf = new PerformanceImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
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
        String query = "UPDATE performance SET spectacle_id='"+entity.getSpectacle_id()+"', troupe_id='"+entity.getTroupe_id()+"' WHERE performance_id="+entity.getId()+";";
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
        String query = "DELETE FROM performance WHERE performance_id="+key+";";//возможно нужно каскадное уаление
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
}
