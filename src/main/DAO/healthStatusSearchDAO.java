package main.DAO;

import main.DTO.healthStatusDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class healthStatusSearchDAO {
    private final Connection connection;

    // 생성자: 데이터베이스 연결 초기화
    public healthStatusSearchDAO(Connection connection) {
        this.connection = connection;
    }

    public List<healthStatusDTO> getHealthRecordsWithDateRange(
            String userId,
            String startDate,
            String endDate,
            String achievementStatus,
            Integer avgCalorieAmount,
            boolean isGreaterThan, boolean isLessThan) throws SQLException {
        List<healthStatusDTO> results = new ArrayList<>();

        // 기본 SQL 쿼리
        String query = "SELECT 건강기록번호, 사용자ID, TO_CHAR(날짜, 'YYYY-MM-DD') AS 날짜, 총칼로리소비량, " +
                "목표달성률, 목표달성여부, 평균칼로리소비량, 평균대비칼로리소비량 " + "FROM 건강상태기록 WHERE 1=1";

        // 조건부 쿼리 추가
        if (userId != null && !userId.isEmpty()) query += " AND 사용자ID = ?";
        if (startDate != null && !startDate.isEmpty()) query += " AND 날짜 >= TO_DATE(?, 'YYYY-MM-DD')";
        if (endDate != null && !endDate.isEmpty()) query += " AND 날짜 <= TO_DATE(?, 'YYYY-MM-DD')";
        if (achievementStatus != null && !achievementStatus.isEmpty()) query += " AND 목표달성여부 = ?";

        // 평균 칼로리 소비량 필터링 추가
        if (avgCalorieAmount != null) {
            if (isGreaterThan) {
                query += " AND 평균대비칼로리소비량 >= ?";
            } else if (isLessThan) {
                query += " AND 평균대비칼로리소비량 <= ?";
            } else {
                // 기본값으로 '이하' 조건 설정
                query += " AND 평균대비칼로리소비량 <= ?";
            }
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;

            // 조건 파라미터 설정
            if (userId != null && !userId.isEmpty()) stmt.setString(index++, userId);
            if (startDate != null && !startDate.isEmpty()) stmt.setString(index++, startDate);
            if (endDate != null && !endDate.isEmpty()) stmt.setString(index++, endDate);
            if (achievementStatus != null && !achievementStatus.isEmpty()) stmt.setString(index++, achievementStatus);

            // 평균 칼로리 소비량 파라미터 설정
            if (avgCalorieAmount != null) {
                stmt.setInt(index++, avgCalorieAmount);
            }

            // 결과 처리
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    healthStatusDTO dto = new healthStatusDTO(
                            rs.getInt("건강기록번호"),             // 건강 기록 번호
                            rs.getString("사용자ID"),             // 사용자 ID
                            rs.getString("날짜"),                 // 날짜
                            rs.getInt("총칼로리소비량"),          // 총 칼로리 소비량
                            rs.getDouble("목표달성률"),            // 목표 달성률
                            rs.getString("목표달성여부"),         // 목표 달성 여부
                            rs.getDouble("평균칼로리소비량"),     // 평균 칼로리 소비량
                            rs.getDouble("평균대비칼로리소비량")  // 평균 대비 칼로리 소비량
                    );
                    results.add(dto);
                }
            }
        }
        return results; // 조회 결과 반환
    }
}
