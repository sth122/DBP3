package main.Preparedstetement;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterUser {
    public RegisterUser(String userId, String name, int age, String gender) {
        // 성별 입력 검증
        if (!gender.equals("남성") && !gender.equals("여성")) {
            JOptionPane.showMessageDialog(null, "성별은 '남성' 또는 '여성'으로 입력하세요.");
            return; // 입력이 올바르지 않으므로 실행 중단
        }

        String jdbcUrl = "jdbc:oracle:thin:@//localhost:1521/XE";  // 데이터베이스 연결 URL (수정 필요)
        String username = "DBP3";  // DB 사용자명 (수정 필요)
        String password = "1234";  // DB 비밀번호 (수정 필요)

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 데이터베이스 연결
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // SQL 쿼리 준비
            String query = "INSERT INTO 사용자 (사용자ID, 이름, 나이, 성별) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            // 파라미터 설정
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, age);  // 나이를 int로 설정
            preparedStatement.setString(4, gender);

            // 쿼리 실행
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "사용자가 성공적으로 등록되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "사용자 등록에 실패했습니다.");
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            // 데이터베이스 오류가 발생했을 때 처리
            if (e.getErrorCode() == 1) {  // ORA-00001: 중복된 PK 에러
                JOptionPane.showMessageDialog(null, "userID가 중복되었습니다. 다른 ID를 사용하세요.");
            } else {
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
