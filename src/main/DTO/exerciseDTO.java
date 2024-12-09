package main.DTO;

public class exerciseDTO {
    private int recordId;         // 기록 번호
    private String userId;        // 사용자 ID
    private String date;          // 운동 날짜
    private String exerciseType;  // 운동 종류
    private int duration;         // 운동 시간 (분)
    private int calories;         // 소비 칼로리

    // 기본 생성자
    public exerciseDTO() {}

    // 모든 필드를 초기화하는 생성자
    public exerciseDTO(int recordId, String userId, String date, String exerciseType, int duration, int calories) {
        this.recordId = recordId;
        this.userId = userId;
        this.date = date;
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.calories = calories;
    }

    // Getter와 Setter
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
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

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "exerciseDTO{" +
                "recordId=" + recordId +
                ", userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", exerciseType='" + exerciseType + '\'' +
                ", duration=" + duration +
                ", calories=" + calories +
                '}';
    }
}
