package dao.impl.report;

import entity.impl.PlayReportImpl;
import util.DbUtil;
import service.ConnectionFactory;
import util.SortUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class PlayReportDao {
    private Connection connection;
    private Statement statement;
    private static PlayReportDao dao;

    private PlayReportDao(){}

    public static PlayReportDao getInstance(){
        if(dao == null)
            dao = new PlayReportDao();
        return dao;
    }
    public List<PlayReportImpl> getAll(){
        List<PlayReportImpl> plays = new LinkedList<>();
        String secQuery = "SELECT play.play_id, spectacle.name, spectacle.summary, troupe.name, date, time, actors, theatre.name, prices " +
                "FROM spectacle, troupe, play, performance, actor, role, contract, audience, theatre, " +
                    "(SELECT performance.performance_id, GROUP_CONCAT(actor.name SEPARATOR ',') AS actors " +
                    "FROM play, performance, actor, role " +
                    "WHERE play.performance_id = performance.performance_id " +
                    "AND performance.performance_id=role.performance_id " +
                    "AND role.actor_id=actor.actor_id " +
                    "GROUP BY play.play_id) AS actors_in_play " +
                "WHERE play.performance_id = performance.performance_id " +
                "AND spectacle.id = performance.spectacle_id " +
                "AND performance.performance_id=role.performance_id " +
                "AND role.actor_id=actor.actor_id " +
                "AND actors_in_play.performance_id = performance.performance_id " +
                "AND contract.play_id = play.play_id "+
                "AND contract.audience_id = audience.audience_id "+
                "AND theatre.id = audience.theatre_id "+
                "AND troupe.id = performance.troupe_id "+
                "AND date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 MONTH) "+
                "GROUP BY play.play_id;";
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(secQuery);
            while (rs.next()){
                PlayReportImpl play = new PlayReportImpl();
                play.setId(rs.getLong(1));
                play.setName(rs.getString(2));
                play.setSummary(rs.getString(3));
                play.setTroupe(rs.getString(4));
                play.setDate(rs.getString(5));
                play.setTime(rs.getString(6));
                play.setStarring(rs.getString(7));
                play.setTheatre(rs.getString(8));
                play.setPrices(rs.getString(9));
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

    public List<PlayReportImpl> getFiltered(String search, String theatre, String dateFrom, String dateTo, String period, String sort) {
        List<PlayReportImpl> plays = new LinkedList<>();
        List<String> sortUtils = SortUtils.playReportUtils(period, sort);
        String query = "SELECT spectacle.name, spectacle.summary, troupe.name, date, time, actors, theatre.name, prices " +
                "FROM spectacle, troupe, play, performance, actor, role, contract, audience, theatre, " +
                "(SELECT performance.performance_id, GROUP_CONCAT(actor.name SEPARATOR ',') AS actors " +
                "FROM play, performance, actor, role " +
                "WHERE play.performance_id = performance.performance_id " +
                "AND performance.performance_id=role.performance_id " +
                "AND role.actor_id=actor.actor_id " +
                "GROUP BY play.play_id) AS actors_in_play " +
                "WHERE play.performance_id = performance.performance_id " +
                "AND spectacle.id = performance.spectacle_id " +
                "AND performance.performance_id=role.performance_id " +
                "AND role.actor_id=actor.actor_id " +
                "AND actors_in_play.performance_id = performance.performance_id " +
                "AND contract.play_id = play.play_id "+
                "AND contract.audience_id = audience.audience_id "+
                "AND theatre.id = audience.theatre_id "+
                "AND troupe.id = performance.troupe_id ";
        if(search != null && !search.equals(""))
            query += "AND UPPER(spectacle.name) = \"" + search + "\" ";
        if(theatre != null)
            query += "AND theatre.id = "+ theatre + " ";

        if(dateFrom != null && dateTo != null){
            if(!dateFrom.equals(""))
                query+="AND date >= CONVERT('"+dateFrom+"', DATE) ";
            else
                query+="AND date >= CURDATE() ";
            if(!dateTo.equals(""))
                query += "AND date <= CONVERT('"+dateTo+"', DATE) ";
        }
        if(period != null && dateFrom != null && dateTo != null && !dateFrom.equals("") && !dateTo.equals(""))
            query += "AND "+sortUtils.get(0) + " ";

        query += "GROUP BY play.play_id ";

        if(sort != null)
            query += "ORDER BY "+sortUtils.get(1) + ";";
        else
            query +=";";
        System.out.println(query);
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                PlayReportImpl play = new PlayReportImpl();
                play.setName(rs.getString(1));
                play.setSummary(rs.getString(2));
                play.setTroupe(rs.getString(3));
                play.setDate(rs.getString(4));
                play.setTime(rs.getString(5));
                play.setStarring(rs.getString(6));
                play.setTheatre(rs.getString(7));
                play.setPrices(rs.getString(8));
                plays.add(play);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        if(sort != null && (sort.equals("1") || sort.equals("2")))
            if(sort.equals("1"))
                plays.sort(PlayReportImpl.comparatorAsc);
            else
                plays.sort(PlayReportImpl.comparatorDesc);
        return plays;
    }
}
