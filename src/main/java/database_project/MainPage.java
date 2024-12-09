package main.java.database_project;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainPage() {
        setTitle("메인 페이지");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);
        setLocationRelativeTo(null);

        Color color2 = new Color(224,255,255);
        Color color = new Color(173,216,230);

        // CardLayout을 사용하는 패널
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150)); // 여백 추가
        mainPanel.setBackground(color); // 배경색 설정

        JButton registerButton = new JButton("회원가입");
        JButton searchButton = new JButton("조회");
        JButton inputButton = new JButton("등록");

        // 버튼 색상 변경
        registerButton.setBackground(color2);
        searchButton.setBackground(color2);
        inputButton.setBackground(color2);

        // 폰트 스타일 변경
        Font buttonFont = new Font("Serif", Font.BOLD, 16);
        registerButton.setFont(buttonFont);
        searchButton.setFont(buttonFont);
        inputButton.setFont(buttonFont);

        // 이벤트 리스너 추가 (페이지 전환)
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "register"));
        searchButton.addActionListener(e -> cardLayout.show(cardPanel, "search"));
        inputButton.addActionListener(e -> cardLayout.show(cardPanel, "input"));

        mainPanel.add(registerButton);
        mainPanel.add(searchButton);
        mainPanel.add(inputButton);

        // 패널을 카드 레이아웃에 추가
        cardPanel.add(mainPanel, "main");

        // 각 세부 페이지 추가 (뒤로가기 포함)
        cardPanel.add(new RegisterUserPage(this), "register");
        cardPanel.add(new SearchPage(this), "search");
        cardPanel.add(new InputPage(this), "input");

        // 메인 프레임에 cardPanel 추가
        add(cardPanel);
        setVisible(true);
    }

    // 메인 페이지로 돌아가는 메서드
    public void showMainPage() {
        cardLayout.show(cardPanel, "main");
    }

    public static void main(String[] args) {
        new MainPage();
    }
}

