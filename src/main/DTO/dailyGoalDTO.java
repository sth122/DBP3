package main.DTO;

public class dailyGoalDTO {
    private int goalId;           // 목표 번호
    private String userId;        // 사용자 ID
    private String date;          // 목표 날짜
    private int calorieGoal;      // 목표 칼로리 소비량

    // 기본 생성자
    public dailyGoalDTO() {}

    // 모든 필드를 초기화하는 생성자
    public dailyGoalDTO(int goalId, String userId, String date, int calorieGoal) {
        this.goalId = goalId;
        this.userId = userId;
        this.date = date;
        this.calorieGoal = calorieGoal;
    }

    // Getter와 Setter
    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    @Override
    public String toString() {
        return "dailyGoalDTO{" +
                "goalId=" + goalId +
                ", userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", calorieGoal=" + calorieGoal +
                '}';
    }
}
