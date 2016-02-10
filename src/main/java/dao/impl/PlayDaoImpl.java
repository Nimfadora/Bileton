package dao.impl;

import dao.PlayDao;
import entity.impl.*;
import service.ConnectionFactory;
import util.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PlayDaoImpl implements PlayDao {
    private Connection connection;
    private PreparedStatement statement;
    private static PlayDao dao;

    private PlayDaoImpl() { }

    public static PlayDao getInstance(){
        if(dao == null)
            dao = new PlayDaoImpl();
        return dao;
    }

    private static final String GET_ALL = "SELECT * FROM play";
    private static final String CREATE = "INSERT play SET date= ? , time= ? , performance_id= ? ;";
    private static final String READ = "SELECT * FROM play WHERE play_id= ? ;";
    private static final String UPDATE = "UPDATE play SET time= ? , date= ? , performance_id= ? WHERE play_id= ? ;";
    private static final String DELETE = "DELETE FROM play WHERE play_id= ? ;";
    private static final String GET_PLAYS_IN_THEATRES = "SELECT theatre.name, COUNT(play.play_id) AS Count FROM play, theatre, contract, audience " +
            "WHERE contract.play_id = play.play_id " +
            "AND contract.audience_id = audience.audience_id " +
            "AND theatre.id = audience.theatre_id " +
            "GROUP BY theatre.name;";
    private static final String GET_PLAY_AUTO = "SELECT spectacle.name, performance.performance_id, spectacle.id FROM performance, spectacle " +
            "WHERE spectacle.id = performance.spectacle_id;";
    private static final String GET_WITHIN_7_DAYS = "SELECT theatre.name, COUNT(play.play_id) AS Count FROM play, theatre, contract, audience " +
            "WHERE contract.play_id = play.play_id " +
            "AND contract.audience_id = audience.audience_id " +
            "AND theatre.id = audience.theatre_id " +
            "AND play.date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)" +
            "GROUP BY theatre.name;";;


    public List<PlayImpl> getAll(){
        List<PlayImpl> plays = new LinkedList<>();
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            rs = statement.executeQuery();
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
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);
            statement.setDate(1, Date.valueOf(play.getDate()));
            statement.setTime(2, Time.valueOf(play.getTime()));
            statement.setLong(3, play.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public PlayImpl read(Long key) {
        ResultSet rs = null;
        PlayImpl play = new PlayImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(READ);
            statement.setLong(1, key);
            rs = statement.executeQuery();
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
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setTime(1, Time.valueOf(play.getTime()));
            statement.setDate(2, Date.valueOf(play.getDate()));
            statement.setLong(3, play.getPerformance_id());
            statement.setLong(4, play.getId());
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
    public List<PlaysInTheatresImpl> getPlaysInTheatres() {
        ResultSet rs = null;
        List<PlaysInTheatresImpl> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_PLAYS_IN_THEATRES);
            rs = statement.executeQuery();
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
        ResultSet rs = null;
        List<PlayAuto> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_PLAY_AUTO);
            rs = statement.executeQuery();
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
        ResultSet rs = null;
        List<PlaysDuringSevenDaysImpl> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_WITHIN_7_DAYS);
            rs = statement.executeQuery();
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
