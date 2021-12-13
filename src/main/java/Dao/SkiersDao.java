package Dao;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import servlets.SimpleServlet;
import sqlConnection.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SkiersDao {
    private final static Logger logger = Logger.getLogger(SkiersDao.class.getName());
    private final static String AGG_SKIER_TABLE = "aggSkier";
    private final static String AGG_SKIER_VERTICAL_TABLE = "aggSkierVertical";
    private static HikariDataSource dataSource; //hikaricp is faster
    String dbName;

    public SkiersDao(String skiersDbName) {
        this.dbName = skiersDbName;
        dataSource = DataSource.getDataSource(dbName);
    }

    public int getVerticalByDayForSkier(int resortId, int seasonId, int dayId, int skierId){
        int result = 0;
        String findVerticalByDay = " SELECT numRuns" +
                " FROM " + AGG_SKIER_TABLE +
                " WHERE resortID = ?" +
                " AND seasonID = ?" +
                " AND dayID = ?" +
                " AND skierID = ?";
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(findVerticalByDay);
            preparedStatement.setInt(1, resortId);
            preparedStatement.setInt(2, seasonId);
            preparedStatement.setInt(3, dayId);
            preparedStatement.setInt(4, skierId);
            logger.info(preparedStatement.toString());
            results = preparedStatement.executeQuery();
            if (results.next()) result = results.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getTotalVerticalForSkier(int skierId){
        int result = 0;
        String findTotalVertical = " SELECT numRuns" +
                " FROM " +
                AGG_SKIER_VERTICAL_TABLE + ////todo table name
                " WHERE skierID = ?;";
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(findTotalVertical);
            preparedStatement.setInt(1,skierId);
            results = preparedStatement.executeQuery();
            if (results.next()) result = results.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
