package main.Preparedstetement;

import main.callableStatement.*;

import javax.swing.*;
import java.sql.*;

public class InputRegisterExercise {
    public InputRegisterExercise(String userId, Date exerciseDate, String exerciseType, Integer exerciseTimeInt) {
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
            String query = "INSERT INTO 운동기록 (사용자ID, 날짜, 운동종류, 운동시간) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            // 파라미터 설정
            preparedStatement.setString(1, userId);
            preparedStatement.setDate(2, exerciseDate); // java.sql.Date 사용
            preparedStatement.setString(3, exerciseType);
            preparedStatement.setInt(4, exerciseTimeInt); // 운동 시간 (분)

            // 쿼리 실행
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // 칼로리 계산 프로시저 호출
                ExerciseCalorieCalculation calorieCalculator = new ExerciseCalorieCalculation();
                calorieCalculator.calculateCalorie();

                AllCalorieCalculation allCalorieCalculation = new AllCalorieCalculation();
                allCalorieCalculation.allCalorieCalculation();

                AchievementOfGoalCalculation goalCalculation = new AchievementOfGoalCalculation();
                goalCalculation.goalCalculation();

                GenderAverageCalories genderAverageCalories = new GenderAverageCalories();
                genderAverageCalories.genderAverageCalories();

                RelativeAverageCalorie relativeAverageCalorie = new RelativeAverageCalorie();
                relativeAverageCalorie.relativeAverageCalorie();

                JOptionPane.showMessageDialog(null, "운동 기록이 등록되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "운동 기록 등록에 실패했습니다.");
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            // SQL 오류 처리
            JOptionPane.showMessageDialog(null, "데이터베이스 오류가 발생했습니다.");
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
