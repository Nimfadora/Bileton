package dao.impl;

import dao.EditorDao;
import service.ConnectionFactory;
import util.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class EditorDaoImpl implements EditorDao{
    private Connection connection;
    private Statement statement;
    private static EditorDao dao;

    private EditorDaoImpl() {
    }

    public static EditorDao getInstance(){
        if(dao == null)
            dao = new EditorDaoImpl();
        return dao;
    }

    public List performQuery(String query){
        ResultSet rs = null;
        List<List> attrs = new LinkedList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                List entity = new LinkedList<>();
                for(int i=1; i<=rs.getMetaData().getColumnCount(); i++) {
                    entity.add(rs.getMetaData().getColumnName(i));
                    entity.add(rs.getObject(i));
                }
                attrs.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return attrs;
    }
}
