package dao.impl;

import dao.AudienceDao;
import entity.impl.AudienceImpl;
import entity.impl.PlaceImpl;
import service.ConnectionFactory;
import util.DbUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AudienceDaoImpl implements AudienceDao{
    private Connection connection;
    private PreparedStatement statement;
    private static AudienceDao dao;

    private final static String GET_ALL = "SELECT place.* FROM play, place, audience, contract  WHERE play.play_id = ? AND play.play_id = contract.play_id AND audience.audience_id = contract.audience_id and place.audience_id = audience.audience_id Order By place.row_number, place.place_number;";
    private AudienceDaoImpl() { }

    public static AudienceDao getInstance(){
        if(dao == null)
            dao = new AudienceDaoImpl();
        return dao;
    }


    @Override
    public void create(PlaceImpl entity) {

    }

    @Override
    public PlaceImpl read(Long key) {
        return null;
    }

    @Override
    public void update(PlaceImpl entity) {

    }

    @Override
    public void delete(Long key) {

    }

    @Override
    public List<PlaceImpl> getAll() {
        return null;
    }

    public List<PlaceImpl> getAll( Long playId) {
        ResultSet rs = null;
        List<PlaceImpl> audience = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            statement.setLong(1, playId);
            rs = statement.executeQuery();
            while (rs.next()){
                PlaceImpl place = new PlaceImpl();
                place.setId(rs.getLong(1));
                place.setAudienceId(rs.getLong(2));
                place.setCategory(rs.getInt(3));
                place.setRowNum(rs.getInt(4));
                place.setPlaceNum(rs.getInt(5));
                place.setState((rs.getInt(6) != 1));

                audience.add(place);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return audience;
    }
}
