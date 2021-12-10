package Dao;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import sqlConnection.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LiftRideDao {
    private static HikariDataSource resortDataSource; //hikaricp is faster
    private static HikariDataSource skierDataSource;

    public LiftRideDao() {
        resortDataSource = DataSource.getDataSource("resort_db_name"); //todo actual db name
        skierDataSource = DataSource.getDataSource("skier_db_name"); //todo actual db name
    }
    public int getUniqueSkierForResort(int resortId, int seasonId, int dayId){
        int result = 0;
        String findAllUniqueSkier = " SELECT COUNT( DISTINCT skierId) as count " +
                "FROM resorts" +//todo table name
                "WHERE resortId = ? " +
                "AND seasonId = ?" +
                "AND dayId = ?";
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;
        try {
            conn = resortDataSource.getConnection();
            preparedStatement = conn.prepareStatement(findAllUniqueSkier);
            preparedStatement.setInt(1,resortId);
            preparedStatement.setInt(2,seasonId);
            preparedStatement.setInt(3,dayId);
            results = preparedStatement.executeQuery();
            result = results.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getVerticalByDayForSkier(int resortId, int seasonId, int dayId, int skierId){
        int result = 0;
        String findVerticalByDay = " SELECT COUNT (skierId) as count" +
                "FROM skiers" +////todo table name
                "WHERE resortId = ?" +
                "AND seasonId = ?" +
                "AND dayId = ?" +
                "AND skierId = ?";
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;
        try {
            conn = skierDataSource.getConnection();
            preparedStatement = conn.prepareStatement(findVerticalByDay);
            preparedStatement.setInt(1,resortId);
            preparedStatement.setInt(2,seasonId);
            preparedStatement.setInt(3,dayId);
            preparedStatement.setInt(4,skierId);
            results = preparedStatement.executeQuery();
            result = results.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getTotalVerticalForSkier(int skierId){
        int result = 0;
        String findTotalVertical = " SELECT COUNT (skierId) as count" +
                "FROM skiers" +////todo table name
                "WHERE skierId = ?";
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;
        try {
            conn = skierDataSource.getConnection();
            preparedStatement = conn.prepareStatement(findTotalVertical);
            preparedStatement.setInt(1,skierId);
            results = preparedStatement.executeQuery();
            result = results.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
