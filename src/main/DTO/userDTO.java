package main.DTO;

public class userDTO {
    private String userId;  // 사용자 ID
    private String name;    // 이름
    private int age;        // 나이
    private String gender;  // 성별

    public userDTO(String userId, String name, int age, String gender) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "userDTO{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}