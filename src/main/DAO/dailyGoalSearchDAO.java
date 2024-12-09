package main.DAO;

import main.DTO.dailyGoalDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dailyGoalSearchDAO {
    private final Connection connection;

    public dailyGoalSearchDAO(Connection connection) {
        this.connection = connection;
    }

    // 목표 칼로리 소비량 이상/이하 처리 추가된 메서드
    public List<dailyGoalDTO> getDailyGoals(String userId, String date, Integer calorieAmount, boolean isGreaterThan)
            throws SQLException {
        List<dailyGoalDTO> results = new ArrayList<>();
        String query = "SELECT 목표기록번호, 사용자ID, TO_CHAR(날짜, 'YYYY-MM-DD') AS 날짜, 목표칼로리소비량 " +
                    "FROM 금일목표설정 WHERE 1=1";

        // 조건 추가
        if (userId != null && !userId.isEmpty()) query += " AND 사용자ID = ?";
        if (date != null && !date.isEmpty()) query += " AND 날짜 = ?";
        if (calorieAmount != null) {
            query += isGreaterThan ? " AND 목표칼로리소비량 >= ?" : " AND 목표칼로리소비량 <= ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;
            if (userId != null && !userId.isEmpty()) stmt.setString(index++, userId);
            if (date != null && !date.isEmpty()) stmt.setString(index++, date);
            if (calorieAmount != null) stmt.setInt(index++, calorieAmount);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dailyGoalDTO dto = new dailyGoalDTO(
                            rs.getInt("목표기록번호"),
                            rs.getString("사용자ID"),
                            rs.getString("날짜"),
                            rs.getInt("목표칼로리소비량")
                    );
                    results.add(dto);
                }
            }
        }
        return results;
    }
}
