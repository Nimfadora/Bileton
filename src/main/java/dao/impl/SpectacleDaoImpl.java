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
    private Statement statement;
    private static SpectacleDao dao;

    private SpectacleDaoImpl() {
    }

    public static SpectacleDao getInstance(){
        if(dao == null)
            dao = new SpectacleDaoImpl();
        return dao;
    }

    @Override
    public void create(SpectacleImpl entity) {
        String query = "INSERT spectacle SET name='"+entity.getName()+"', summary='"+entity.getSummary()+"' ;";
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
    public SpectacleImpl read(Long key) {
        String query = "SELECT * FROM spectacle WHERE id="+key+";";
        ResultSet rs = null;
        SpectacleImpl spectacle = new SpectacleImpl();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
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
        String query = "UPDATE spectacle SET summary='"+entity.getSummary()+"', name='"+entity.getName()+"' WHERE id='"+entity.getId()+"';";
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
        String query = "DELETE FROM spectacle WHERE id="+key+";";//возможно нужно каскадное уаление
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
    public List<SpectacleImpl> getAll() {
        String query = "SELECT * FROM spectacle;";
        ResultSet rs = null;
        List<SpectacleImpl> spectacles = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
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

    public static final String SEARCH_ALL_THE_REST = "SELECT * FROM spectacle WHERE UPPER(spectacle.name) != ? ;";
    public static final String SEARCH = "SELECT * FROM spectacle WHERE UPPER(spectacle.name) = ?;";

    public static final String SORT_ASC = "SELECT * FROM spectacle ORDER BY spectacle.name ASC;";
    public static final String SORT_DESC = "SELECT * FROM spectacle ORDER BY spectacle.name DESC;";

    public static final String SEARCH_ALL_THE_REST_SORT_ASC = "SELECT * FROM spectacle WHERE UPPER(spectacle.name) != ? ORDER BY spectacle.name ASC;";
    public static final String SEARCH_ALL_THE_REST_SORT_DESC = "SELECT * FROM spectacle WHERE UPPER(spectacle.name) != ? ORDER BY spectacle.name DESC;";

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
