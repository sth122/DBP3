package main.callableStatement;

import java.sql.*;

public class GenderAverageCalories {
    private static final String JDBC_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
    private static final String USERNAME = "DBP3";
    private static final String PASSWORD = "1234";

    public void genderAverageCalories() {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            // JDBC 연결
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // 일괄갱신 in JDBC 트랜잭션
            connection.setAutoCommit(false);

            // SP_성별평균칼로리소비량_계산 프로시저 호출
            String sql = "{ call SP_성별평균칼로리소비량_계산 }";
            callableStatement = connection.prepareCall(sql);

            // 프로시저 실행
            callableStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            try {
                if (callableStatement != null) callableStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
