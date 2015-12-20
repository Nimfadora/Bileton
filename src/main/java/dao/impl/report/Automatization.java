package dao.impl.report;

import entity.impl.AutoImpl;
import service.ConnectionFactory;
import util.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Automatization {

    //TODO: callable statement хранимые процедуры

    private Connection connection;
    private Statement statement;
    private static Automatization dao;

    private Automatization(){}

    public static Automatization getInstance(){
        if(dao == null)
            dao = new Automatization();
        return dao;
    }

    public void fillAudience(Long audience_id) {
        String query = "INSERT INTO place VALUES ";
        int category = 1;
        for (int i = 1; i < 15; i++) {
//            if (i == 4 || i == 10)
//                category++;
            for (int j = 1; j < 19; j++) {
                if (!(i == 12 && j > 16) && !(i == 13 && j > 14) && !(i == 14 && j > 12))
                    query += "(NULL, " + audience_id + ", " + category + ", " + i + ", " + j + "," + "0),";
            }
        }
        query = query.substring(0, query.length() - 1);
        query += ";";
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

    public void createPlay(AutoImpl play) {
        String q1 = "Insert into play Values(null, '" + play.getDate() + "','" + play.getTime() + "' , " + play.getPerfId() + ");";
        //fillAudience()
        String q2 = "Insert into contract Values(";
    }
}
