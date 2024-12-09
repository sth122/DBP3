package main.DAO;

import main.DTO.exerciseDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class exerciseSearchDAO {
    private final Connection connection;

    public exerciseSearchDAO(Connection connection) {
        this.connection = connection;
    }

    // 날짜 범위 추가된 메서드
    public List<exerciseDTO> getExerciseRecordsWithDateRange(
            String userId,
            String startDate,
            String endDate,
            String exerciseType,
            Integer calorieAmount,
            boolean isGreaterThan, boolean isLessThan) throws SQLException {

        List<exerciseDTO> results = new ArrayList<>();
        String query = "SELECT 운동기록번호, 사용자ID, TO_CHAR(날짜, 'YYYY-MM-DD') AS 날짜, 운동종류, 운동시간, 칼로리소비량 " +
                    "FROM 운동기록 WHERE 1=1";

        // 조건 추가
        if (userId != null && !userId.isEmpty()) query += " AND 사용자ID = ?";
        if (startDate != null && !startDate.isEmpty()) query += " AND 날짜 >= ?";
        if (endDate != null && !endDate.isEmpty()) query += " AND 날짜 <= ?";
        if (exerciseType != null && !exerciseType.isEmpty()) query += " AND 운동종류 = ?";
        if (calorieAmount != null) {
            query += isGreaterThan ? " AND 칼로리소비량 >= ?" : " AND 칼로리소비량 <= ?";
        }

        // PreparedStatement 실행
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;
            if (userId != null && !userId.isEmpty()) stmt.setString(index++, userId);
            if (startDate != null && !startDate.isEmpty()) stmt.setString(index++, startDate);
            if (endDate != null && !endDate.isEmpty()) stmt.setString(index++, endDate);
            if (exerciseType != null && !exerciseType.isEmpty()) stmt.setString(index++, exerciseType);
            if (calorieAmount != null) stmt.setInt(index++, calorieAmount);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    exerciseDTO dto = new exerciseDTO(
                            rs.getInt("운동기록번호"),
                            rs.getString("사용자ID"),
                            rs.getString("날짜"),
                            rs.getString("운동종류"),
                            rs.getInt("운동시간"),
                            rs.getInt("칼로리소비량")
                    );
                    results.add(dto);
                }
            }
        }
        return results;
    }
}
