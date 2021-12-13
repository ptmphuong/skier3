package Dao;

import com.zaxxer.hikari.HikariDataSource;
import sqlConnection.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ResortsDao {
    private final static Logger logger = Logger.getLogger(ResortsDao.class.getName());
    private static HikariDataSource dataSource;
    String dbName;

    public ResortsDao(String skiersDbName) {
        this.dbName = skiersDbName;
        dataSource = DataSource.getDataSource(dbName);
    }

    public int getUniqueSkierForResort(int resortId, int seasonId, int dayId){
        int result = 0;
        String findAllUniqueSkier = " SELECT numSkiers " +
                " FROM " +
                this.dbName +
                " WHERE resortID = ? " +
                " AND seasonID = ?" +
                " AND dayID = ?";
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(findAllUniqueSkier);
            preparedStatement.setInt(1, resortId);
            preparedStatement.setInt(2, seasonId);
            preparedStatement.setInt(3, dayId);
            results = preparedStatement.executeQuery();
            if (results.next()) result = results.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
