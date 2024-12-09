package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Conn_Query {
    private Connection con = null;

    public DB_Conn_Query() {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String id = "DBP3";
        String password = "1234";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("드라이버 적재 성공");
            this.con = DriverManager.getConnection(url, id, password);
            System.out.println("데이터베이스 연결 성공");
        } catch (ClassNotFoundException var5) {
            ClassNotFoundException e = var5;
            System.out.println("드라이버를 찾을 수 없습니다: " + e.getMessage());
        } catch (SQLException var6) {
            SQLException e = var6;
            System.out.println("연결에 실패하였습니다: " + e.getMessage());
        }

    }

    public Connection getConnection() {
        return this.con;
    }

    public void closeConnection() {
        if (this.con != null) {
            try {
                this.con.close();
                System.out.println("데이터베이스 연결 닫기 성공");
            } catch (SQLException var2) {
                SQLException e = var2;
                System.out.println("연결을 닫는 데 실패하였습니다: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        DB_Conn_Query dbConnQuery = new DB_Conn_Query();
        if (dbConnQuery.getConnection() != null) {
            System.out.println("DB 연결 테스트 성공!");
            dbConnQuery.closeConnection();
        } else {
            System.out.println("DB 연결 테스트 실패!");
        }
    }
}
