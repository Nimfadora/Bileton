package dao.impl;

import dao.TheatreDao;
import entity.impl.TheatreImpl;
import util.DbUtil;
import service.ConnectionFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TheatreDaoImpl implements TheatreDao {
    private Connection connection;
    private PreparedStatement statement;
    private static TheatreDao dao;

    private TheatreDaoImpl() { }

    public static TheatreDao getInstance(){
        if(dao == null)
            dao = new TheatreDaoImpl();
        return dao;
    }

    private static final String CREATE = "INSERT theatre SET name= ? ;";
    private static final String READ = "SELECT * FROM theatre WHERE id= ? ;";
    private static final String UPDATE = "UPDATE theatre SET name= ? WHERE id= ? ;";
    private static final String DELETE = "DELETE FROM theatre WHERE id= ? ;";
    private static final String GET_ALL = "SELECT * FROM theatre";

    public static final String SEARCH_ALL_THE_REST = "SELECT * FROM theatre WHERE UPPER(theatre.name) != ? ;";
    public static final String SEARCH = "SELECT * FROM theatre WHERE UPPER(theatre.name) = ?;";

    public static final String SORT_ASC = "SELECT * FROM theatre ORDER BY theatre.name ASC;";
    public static final String SORT_DESC = "SELECT * FROM theatre ORDER BY theatre.name DESC;";

    public static final String SEARCH_ALL_THE_REST_SORT_ASC = "SELECT * FROM theatre WHERE UPPER(theatre.name) != ? ORDER BY theatre.name ASC;";
    public static final String SEARCH_ALL_THE_REST_SORT_DESC = "SELECT * FROM theatre WHERE UPPER(theatre.name) != ? ORDER BY theatre.name DESC;";

    @Override
    public void create(TheatreImpl theatre) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);
            statement.setString(1, theatre.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public TheatreImpl read(Long key) {
        ResultSet rs = null;
        TheatreImpl theatre = new TheatreImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(READ);
            statement.setLong(1, key);
            rs = statement.executeQuery();
            while (rs.next()) {
                theatre.setId(rs.getLong(1));
                theatre.setName(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return theatre;
    }

    @Override
    public void update(TheatreImpl theatre) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, theatre.getName());
            statement.setLong(2, theatre.getId());
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
    public List<TheatreImpl> getAll() {
        ResultSet rs = null;
        List<TheatreImpl> theatres = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            rs = statement.executeQuery();
            while (rs.next()){
                TheatreImpl theatre = new TheatreImpl();
                theatre.setId(rs.getLong(1));
                theatre.setName(rs.getString(2));
                theatres.add(theatre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return theatres;
    }

    @Override
    public List<TheatreImpl> search(String searchQuery, String sort) {
        ResultSet rs = null;
        PreparedStatement searchResult = null;
        PreparedStatement allOtherResults;
        List<TheatreImpl> theatres = new LinkedList<>();
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
                    TheatreImpl theatre = new TheatreImpl();
                    theatre.setId(rs.getLong(1));
                    theatre.setName(rs.getString(2));
                    theatre.setCategory("selected");
                    theatres.add(theatre);
                }
            }
            rs = allOtherResults.executeQuery();
            while (rs.next()){
                TheatreImpl theatre = new TheatreImpl();
                theatre.setId(rs.getLong(1));
                theatre.setName(rs.getString(2));
                theatres.add(theatre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return theatres;
    }
}
