package dao.impl;

import dao.PlayDao;
import entity.impl.*;
import service.ConnectionFactory;
import util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class PlayDaoImpl implements PlayDao {
    private Connection connection;
    private Statement statement;
    private static PlayDao dao;

    private PlayDaoImpl() { }

    public static PlayDao getInstance(){
        if(dao == null)
            dao = new PlayDaoImpl();
        return dao;
    }

    public List<PlayImpl> getAll(){
        List<PlayImpl> plays = new LinkedList<>();
        String secQuery = "SELECT * FROM play";
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(secQuery);
            while (rs.next()){
                PlayImpl play = new PlayImpl();
                play.setId(rs.getLong(1));
                play.setDate(rs.getString(2));
                play.setTime(rs.getString(3));
                play.setPerformance_id(rs.getLong(4));
                plays.add(play);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return plays;
    }

    @Override
    public void create(PlayImpl play) {
        String query = "INSERT play SET date='"+play.getDate()+"', time='"+play.getTime()+"', performance_id="+play.getPerformance_id()+";";
        //INSERT play SET date='2015-12-11', time='21:00', performance_id=1;
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
    public PlayImpl read(Long key) {
        String query = "SELECT * FROM play WHERE play_id="+key+";";
        ResultSet rs = null;
        PlayImpl play = new PlayImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                play.setId(rs.getLong(1));
                play.setDate(rs.getString(2));
                play.setTime(rs.getString(3));
                play.setPerformance_id(rs.getLong(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return play;
    }

    @Override
    public void update(PlayImpl play) {
        String query = "UPDATE play SET time='"+play.getTime()+"', date='"+play.getDate()+"', performance_id="+play.getPerformance_id()+" WHERE play_id="+play.getId()+";";
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
        String query = "DELETE FROM play WHERE play_id="+key+";";//возможно нужно каскадное уаление
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
    public List<PlaysInTheatresImpl> getPlaysInTheatres() {
        String query = "SELECT theatre.name, COUNT(play.play_id) AS Count FROM play, theatre, contract, audience " +
                "WHERE contract.play_id = play.play_id " +
                "AND contract.audience_id = audience.audience_id " +
                "AND theatre.id = audience.theatre_id " +
                "GROUP BY theatre.name;";
        ResultSet rs = null;
        List<PlaysInTheatresImpl> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                PlaysInTheatresImpl pit = new PlaysInTheatresImpl();
                pit.setName(rs.getString(1));
                pit.setCount(rs.getInt(2));
                lst.add(pit);
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

    public List<PlayAuto> getPlayAuto(){
        String query = "SELECT spectacle.name, performance.performance_id, spectacle.id FROM performance, spectacle " +
                "WHERE spectacle.id = performance.spectacle_id;";
        ResultSet rs = null;
        List<PlayAuto> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                PlayAuto pit = new PlayAuto();
                pit.setSpectacle(rs.getString(1));
                pit.setPerfId(rs.getLong(2));
                pit.setSpId(rs.getLong(3));
                lst.add(pit);
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
    public List<PlaysDuringSevenDaysImpl> getPlaysDuringSevenDays() {
        String query = "SELECT theatre.name, COUNT(play.play_id) AS Count FROM play, theatre, contract, audience " +
                "WHERE contract.play_id = play.play_id " +
                "AND contract.audience_id = audience.audience_id " +
                "AND theatre.id = audience.theatre_id " +
                "AND play.date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)" +
                "GROUP BY theatre.name;";
        ResultSet rs = null;
        List<PlaysDuringSevenDaysImpl> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                PlaysDuringSevenDaysImpl pit = new PlaysDuringSevenDaysImpl();
                pit.setTheatre(rs.getString(1));
                pit.setCount(rs.getInt(2));
                lst.add(pit);
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
}
