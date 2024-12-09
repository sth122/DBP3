package main.Preparedstetement;

import javax.swing.*;
import java.sql.*;

public class InputRegisterGoal {
    public InputRegisterGoal(String userId, Date sqlDate, Integer goalCalorieInt) {
        String jdbcUrl = "jdbc:oracle:thin:@//localhost:1521/XE"; // 데이터베이스 연결 URL (필요시 수정)
        String username = "DBP3"; // DB 사용자명 (필요시 수정)
        String password = "1234"; // DB 비밀번호 (필요시 수정)

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 데이터베이스 연결
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // SQL 쿼리 준비
            String query = "INSERT INTO 금일목표설정 (사용자ID, 날짜, 목표칼로리소비량) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            // 파라미터 설정
            preparedStatement.setString(1, userId);
            preparedStatement.setDate(2, sqlDate); // java.sql.Date 사용
            preparedStatement.setInt(3, goalCalorieInt);

            // 쿼리 실행
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "목표 설정이 성공적으로 등록되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "목표 설정 등록에 실패했습니다.");
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            // 날짜 중복 오류 처리 (ORA-00001)
            if (e.getErrorCode() == 1) {
                JOptionPane.showMessageDialog(null, "목표 설정 날짜가 이미 존재합니다.");
            } else {    // SQL 오류 처리
                JOptionPane.showMessageDialog(null, "데이터베이스 오류가 발생했습니다.");
            }
            e.printStackTrace();
        } finally {
            // 자원 해제
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
