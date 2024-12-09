package main.java.database_project;

import main.Preparedstetement.InputRegisterExercise;
import main.Preparedstetement.InputRegisterGoal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//InputPage 클래스에 뒤로가기 버튼 추가
public class InputPage extends JPanel {
    public InputPage(MainPage mainPage) {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        Color color = new Color(173,216,230);
        Color color2 = new Color(224,255,255);

        JPanel exercisePanel = new JPanel(new GridLayout(5, 2, 10, 10));

        exercisePanel.add(new JLabel("사용자 ID:"));
        JTextField InputExerciseUserIdField = new JTextField();
        exercisePanel.add(InputExerciseUserIdField);

        exercisePanel.add(new JLabel("날짜:"));
        JTextField InputExerciseDateField = new JTextField();
        exercisePanel.add(InputExerciseDateField);

        exercisePanel.add(new JLabel("운동 종류:"));
        JTextField InputExerciseTypeField = new JTextField();
        exercisePanel.add(InputExerciseTypeField);

        exercisePanel.add(new JLabel("운동 시간:"));
        JTextField InputExerciseTimeField = new JTextField();
        exercisePanel.add(InputExerciseTimeField);


        JButton registerExerciseBtn = new JButton("운동 기록 등록");
        exercisePanel.add(registerExerciseBtn);

        registerExerciseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = InputExerciseUserIdField.getText();
                String exerciseDate = InputExerciseDateField.getText();
                String exerciseType = InputExerciseTypeField.getText();
                String exerciseTime = InputExerciseTimeField.getText();

                // 입력값 유효성 검사
                if ( userId.isEmpty() || exerciseDate.isEmpty() || exerciseType.isEmpty() || exerciseTime.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "모든 필드를 입력해야 합니다.");
                    return;
                }

                // exerciseDate를 java.sql.Date로 변환
                java.sql.Date sqlDate;
                try {
                    java.util.Date utilDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(exerciseDate); // 날짜 형식 검증 및 변환
                    sqlDate = new java.sql.Date(utilDate.getTime()); // java.sql.Date로 변환
                } catch (java.text.ParseException ex) {
                    JOptionPane.showMessageDialog(null, "날짜는 'yyyy-MM-dd' 형식으로 입력해야 합니다.");
                    return;
                }

                // exerciseTime을 int로 변환
                int exerciseTimeInt;
                try {
                    exerciseTimeInt = Integer.parseInt(exerciseTime); // 운동 시간을 int로 변환
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "운동 시간은 숫자로 입력해야 합니다.");
                    return;
                }

                // Oracle DB에 운동 기록 등록
                new InputRegisterExercise(userId, sqlDate, exerciseType, exerciseTimeInt);
            }
        });


        // 목표 설정 등록 패널
        JPanel goalPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        goalPanel.add(new JLabel("사용자 ID:"));
        JTextField InputGoalUserIdField = new JTextField();
        goalPanel.add(InputGoalUserIdField);


        goalPanel.add(new JLabel("목표 칼로리 소비량:"));
        JTextField InputGoalCalorieField = new JTextField();
        goalPanel.add(InputGoalCalorieField);


        goalPanel.add(new JLabel("날짜:"));
        JTextField InputGoalDateField = new JTextField();
        goalPanel.add(InputGoalDateField);


        JButton registerGoalBtn = new JButton("목표 등록");
        goalPanel.add(registerGoalBtn);

        registerGoalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = InputGoalUserIdField.getText();
                String goalDate = InputGoalDateField.getText();
                String goalCalorie = InputGoalCalorieField.getText();

                // 입력값 유효성 검사
                if (userId.isEmpty() || goalDate.isEmpty() || goalCalorie.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "모든 필드를 입력해야 합니다.");
                    return;
                }

                // goalCalorie을 int로 변환
                int goalCalorieInt;
                try {
                    goalCalorieInt = Integer.parseInt(goalCalorie); // 목표칼로리소비량을 int로 변환
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "운동 시간은 숫자로 입력해야 합니다.");
                    return;
                }

                // goalDate를 java.sql.Date로 변환
                java.sql.Date sqlDate;
                try {
                    java.util.Date utilDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(goalDate); // 날짜 형식 검증 및 변환
                    sqlDate = new java.sql.Date(utilDate.getTime()); // java.sql.Date로 변환
                } catch (java.text.ParseException ex) {
                    JOptionPane.showMessageDialog(null, "날짜는 'yyyy-MM-dd' 형식으로 입력해야 합니다.");
                    return;
                }

                // Oracle DB에 사용자 등록
                new InputRegisterGoal(userId, sqlDate, goalCalorieInt);
            }
        });


        exercisePanel.setBackground(color); // 배경색 설정
        goalPanel.setBackground(color); // 배경색 설정

        // 탭 추가
        tabbedPane.addTab("목표 설정 등록", goalPanel);
        tabbedPane.addTab("운동 기록 등록", exercisePanel);


        add(tabbedPane);
        setVisible(true);

        // 뒤로가기 버튼 추가
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> mainPage.showMainPage());
        add(backButton, BorderLayout.SOUTH);

        add(tabbedPane, BorderLayout.CENTER);

        // 버튼 색상 변경
        registerExerciseBtn.setBackground(color2);
        registerGoalBtn.setBackground(color2);
        backButton.setBackground(color2);
    }
}
