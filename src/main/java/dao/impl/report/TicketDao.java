package dao.impl.report;

import entity.impl.TicketImpl;
import entity.impl.TicketStatisticsImpl;
import service.ConnectionFactory;
import util.DbUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TicketDao {
    public static final String RESERVE_QUERY = "UPDATE place SET state = 1 WHERE place_id = ?;";
    public static final String BUY_QUERY = "SELECT place.place_number, place.row_number, troupe.name, actors, spectacle.name, theatre.name, contract.prices, date, time, place.place_id, play.play_id " +
            "FROM spectacle, troupe, play, performance, actor, role, contract, audience, theatre, place, " +
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
            "AND place.audience_id = audience.audience_id "+
            "AND place.place_id = ?;";
    private Connection connection;
    private Statement statement;
    private static TicketDao dao;

    private TicketDao() {
    }

    public static TicketDao getInstance(){
        if(dao == null)
            dao = new TicketDao();
        return dao;
    }

    public List<TicketImpl> buyTicket(Long[] placesId){
        List<TicketImpl> tickets = new LinkedList<>();
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement1 = connection.prepareStatement(BUY_QUERY);
            PreparedStatement statement2 = connection.prepareStatement(RESERVE_QUERY);
            for(Long id:placesId) {
                statement1.setLong(1, id);
                rs = statement1.executeQuery();
                rs.next();
                TicketImpl ticket = new TicketImpl();
                ticket.setPlace(rs.getInt(1));
                ticket.setRow(rs.getInt(2));
                ticket.setTroupe(rs.getString(3));
                ticket.setActors(rs.getString(4));
                ticket.setPlay(rs.getString(5));
                ticket.setTheatre(rs.getString(6));
                ticket.setPrice(rs.getString(7));
                ticket.setDate(rs.getString(8));
                ticket.setTime(rs.getString(9));
                String barcode = String.valueOf(rs.getInt(10));
                barcode += String.valueOf(rs.getInt(11));
                ticket.setBarcode(Long.valueOf(barcode));
                tickets.add(ticket);

                statement2.setLong(1, id);
                statement2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(connection);
        }
        return tickets;
    }
    public void reserveTicket(Long[] ids){
        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(RESERVE_QUERY);
            for(Long id:ids) {
                statement.setLong(1, id);
                statement.execute();
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        for(Long id:ids) {

        }
    }

    public List<TicketStatisticsImpl> getTicketStatistics() {
        String query = "SELECT play.play_id, spectacle.name, COUNT(DISTINCT place.place_id) AS Sold, COUNT(DISTINCT t.place_id) AS Total " +
                "FROM play, performance, spectacle, contract, audience, place, place t " +
                "WHERE contract.play_id = play.play_id " +
                "AND contract.audience_id = audience.audience_id " +
                "AND place.audience_id = audience.audience_id " +
                "AND t.audience_id = audience.audience_id " +
                "AND play.performance_id = performance.performance_id " +
                "AND performance.spectacle_id = spectacle.id " +
                "AND place.state = 1 " +
                "GROUP BY play.play_id, spectacle.name;";
        ResultSet rs = null;
        List<TicketStatisticsImpl> lst = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                TicketStatisticsImpl statistics = new TicketStatisticsImpl();
                statistics.setPlayId(rs.getLong(1));
                statistics.setSpectacle(rs.getString(2));
                statistics.setTicketsSold(rs.getInt(3));
                statistics.setTotal(rs.getInt(4));
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
}
