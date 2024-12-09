package main.java.database_project;

import main.Preparedstetement.RegisterUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUserPage extends JPanel {
    public RegisterUserPage(MainPage mainPage) {
        // RegisterPage의 레이아웃 설정
        setLayout(new BorderLayout());

        // RegisterPanel 생성
        JPanel RegisterPanel = new JPanel();
        Color color = new Color(173, 216, 230);  // 배경색
        Color color2 = new Color(224, 255, 255); // 버튼 색상

        // RegisterPanel에 레이아웃 설정
        RegisterPanel.setLayout(new GridLayout(6, 2, 10, 10));
        RegisterPanel.setBackground(color); // RegisterPanel 배경색 설정

        // 사용자 정보 입력 필드 추가
        RegisterPanel.add(new JLabel("사용자 ID:"));
        JTextField userIdField = new JTextField();
        RegisterPanel.add(userIdField);

        RegisterPanel.add(new JLabel("이름:"));
        JTextField nameField = new JTextField();
        RegisterPanel.add(nameField);

        RegisterPanel.add(new JLabel("나이:"));
        JTextField ageField = new JTextField();  // 나이 입력 필드
        RegisterPanel.add(ageField);

        RegisterPanel.add(new JLabel("성별:"));
        JTextField genderField = new JTextField();
        RegisterPanel.add(genderField);

        // 등록 버튼 추가
        JButton registerButton = new JButton("등록");
        registerButton.setBackground(color2);
        Font buttonFont = new Font("Serif", Font.BOLD, 16);
        registerButton.setFont(buttonFont);
        RegisterPanel.add(registerButton);

        // 뒤로가기 버튼 추가
        JButton backButton = new JButton("뒤로가기");
        backButton.setBackground(color2);
        backButton.setFont(buttonFont);
        backButton.addActionListener(e -> mainPage.showMainPage()); // 메인 페이지로 돌아가기
        RegisterPanel.add(backButton);

        // RegisterPage에 RegisterPanel을 추가
        add(RegisterPanel, BorderLayout.CENTER);

        // 등록 버튼 클릭 이벤트 처리
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String name = nameField.getText();
                String ageText = ageField.getText(); // 나이 텍스트 필드에서 값 가져오기
                String gender = genderField.getText();

                // 입력값 유효성 검사
                if (userId.isEmpty() || name.isEmpty() || ageText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "모든 필드를 입력해야 합니다.");
                    return;
                }

                // 나이를 int로 변환
                int age;
                try {
                    age = Integer.parseInt(ageText);  // 나이를 int로 변환
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "나이는 숫자로 입력해야 합니다.");
                    return;
                }

                // Oracle DB에 사용자 등록
                new RegisterUser(userId, name, age, gender);
            }
        });
    }

}
