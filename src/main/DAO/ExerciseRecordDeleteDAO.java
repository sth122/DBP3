package main.DAO;

import main.callableStatement.AchievementOfGoalCalculation;
import main.callableStatement.GenderAverageCalories;
import main.callableStatement.RelativeAverageCalorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExerciseRecordDeleteDAO {
    private final Connection connection;

    public ExerciseRecordDeleteDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean deleteExerciseRecord(int recordId) throws SQLException {
        String query = "DELETE FROM 운동기록 WHERE 운동기록번호 = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, recordId);
            int rowsAffected = stmt.executeUpdate();
            // 운동 기록 삭제가 성공적으로 수행된 경우 추가 작업 수행
            if (rowsAffected > 0) {
                // AchievementOfGoalCalculation 호출
                AchievementOfGoalCalculation goalCalculation = new AchievementOfGoalCalculation();
                goalCalculation.goalCalculation();

                // GenderAverageCalories 호출
                GenderAverageCalories genderAverageCalories = new GenderAverageCalories();
                genderAverageCalories.genderAverageCalories();

                // RelativeAverageCalorie 호출
                RelativeAverageCalorie relativeAverageCalorie = new RelativeAverageCalorie();
                relativeAverageCalorie.relativeAverageCalorie();
            }

            return rowsAffected > 0; // 삭제 성공 여부 반환
        }
    }
}

