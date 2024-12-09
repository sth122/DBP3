package main.DTO;

public class healthStatusDTO {
    private int healthRecordId; // 건강기록번호
    private String userId; // 사용자ID
    private String date; // 날짜
    private int totalCalories; // 총칼로리소비량
    private double achievementRate; // 목표달성률 (int -> double)
    private String achievementStatus; // 목표달성여부
    private double averageCalories; // 평균칼로리소비량 (int -> double)
    private double caloriesComparedToAverage; // 평균대비칼로리소비량 (int -> double)

    public healthStatusDTO(
            int healthRecordId,
            String userId,
            String date,
            int totalCalories,
            double achievementRate,
            String achievementStatus,
            double averageCalories,
            double caloriesComparedToAverage
    ) {
        this.healthRecordId = healthRecordId;
        this.userId = userId;
        this.date = date;
        this.totalCalories = totalCalories;
        this.achievementRate = achievementRate;
        this.achievementStatus = achievementStatus;
        this.averageCalories = averageCalories;
        this.caloriesComparedToAverage = caloriesComparedToAverage;
    }

    // Getter and Setter
    public int getHealthRecordId() {
        return healthRecordId;
    }

    public void setHealthRecordId(int healthRecordId) {
        this.healthRecordId = healthRecordId;
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

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public double getAchievementRate() {
        return achievementRate;
    }

    public void setAchievementRate(double achievementRate) {
        this.achievementRate = achievementRate;
    }

    public String getAchievementStatus() {
        return achievementStatus;
    }

    public void setAchievementStatus(String achievementStatus) {
        this.achievementStatus = achievementStatus;
    }

    public double getAverageCalories() {
        return averageCalories;
    }

    public void setAverageCalories(double averageCalories) {
        this.averageCalories = averageCalories;
    }

    public double getCaloriesComparedToAverage() {
        return caloriesComparedToAverage;
    }

    public void setCaloriesComparedToAverage(double caloriesComparedToAverage) {
        this.caloriesComparedToAverage = caloriesComparedToAverage;
    }
}