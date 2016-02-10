package dao.impl;

import dao.SpectacleDao;
import entity.impl.SpectacleImpl;
import service.ConnectionFactory;
import util.DbUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SpectacleDaoImpl implements SpectacleDao {
    private Connection connection;
    private PreparedStatement statement;
    private static SpectacleDao dao;

    private SpectacleDaoImpl() {
    }

    public static SpectacleDao getInstance(){
        if(dao == null)
            dao = new SpectacleDaoImpl();
        return dao;
    }

    private static final String CREATE = "INSERT spectacle SET name= ? , summary= ? ;";
    private static final String READ = "SELECT * FROM spectacle WHERE id= ?";
    private static final String UPDATE = "UPDATE spectacle SET summary= ? , name= ? WHERE id= ? ;";
    private static final String DELETE = "DELETE FROM spectacle WHERE id= ? ;";
    private static final String GET_ALL = "SELECT * FROM spectacle;";

    private static final String SEARCH_ALL_THE_REST = "SELECT * FROM spectacle WHERE UPPER(spectacle.name) != ? ;";
    private static final String SEARCH = "SELECT * FROM spectacle WHERE UPPER(spectacle.name) = ?;";

    private static final String SORT_ASC = "SELECT * FROM spectacle ORDER BY spectacle.name ASC;";
    private static final String SORT_DESC = "SELECT * FROM spectacle ORDER BY spectacle.name DESC;";

    private static final String SEARCH_ALL_THE_REST_SORT_ASC = "SELECT * FROM spectacle WHERE UPPER(spectacle.name) != ? ORDER BY spectacle.name ASC;";
    private static final String SEARCH_ALL_THE_REST_SORT_DESC = "SELECT * FROM spectacle WHERE UPPER(spectacle.name) != ? ORDER BY spectacle.name DESC;";

    @Override
    public void create(SpectacleImpl entity) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSummary());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    @Override
    public SpectacleImpl read(Long key) {
        ResultSet rs = null;
        SpectacleImpl spectacle = new SpectacleImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(READ);
            statement.setLong(1, key);
            rs = statement.executeQuery();
            while (rs.next()){
                spectacle.setId(rs.getLong(1));
                spectacle.setName(rs.getString(2));
                spectacle.setSummary(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return spectacle;
    }

    @Override
    public void update(SpectacleImpl entity) {
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, entity.getSummary());
            statement.setString(2, entity.getName());
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

    @Override
    public List<SpectacleImpl> getAll() {
        ResultSet rs = null;
        List<SpectacleImpl> spectacles = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            rs = statement.executeQuery();
            while (rs.next()){
                SpectacleImpl spectacle = new SpectacleImpl();
                spectacle.setId(rs.getLong(1));
                spectacle.setName(rs.getString(2));
                spectacle.setSummary(rs.getString(3));
                spectacles.add(spectacle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return spectacles;
    }



    @Override
    public List<SpectacleImpl> search(String searchQuery, String sort) {
        ResultSet rs = null;
        PreparedStatement searchResult = null;
        PreparedStatement allOtherResults;
        List<SpectacleImpl> spectacles = new LinkedList<>();
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
                    SpectacleImpl spectacle = new SpectacleImpl();
                    spectacle.setId(rs.getLong(1));
                    spectacle.setName(rs.getString(2));
                    spectacle.setSummary(rs.getString(3));
                    spectacle.setCategory("selected");
                    spectacles.add(spectacle);
                }
            }
            rs = allOtherResults.executeQuery();
            while (rs.next()){
                SpectacleImpl spectacle = new SpectacleImpl();
                spectacle.setId(rs.getLong(1));
                spectacle.setName(rs.getString(2));
                spectacle.setSummary(rs.getString(3));
                spectacles.add(spectacle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return spectacles;
    }
}
