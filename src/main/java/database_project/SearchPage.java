package main.java.database_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import main.DAO.*;
import main.DB_Conn_Query;
import main.DTO.dailyGoalDTO;
import main.DTO.exerciseDTO;
import main.DTO.healthStatusDTO;

public class SearchPage extends JPanel {
    private JTable exerciseTable;
    private JTable goalTable;
    private JTable healthTable;

    // 조회 메인 페이지
    public SearchPage(MainPage mainPage) {
        // BorderLayout을 사용하여 페이지 레이아웃 설정
        this.setLayout(new BorderLayout());

        // 탭 패널 생성
        JTabbedPane tabbedPane = new JTabbedPane();

        // 배경색 및 버튼 색상 설정
        Color bgColor = new Color(173, 216, 230); // 밝은 파란색 배경
        Color btnColor = new Color(224, 255, 255); // 밝은 하늘색 버튼

        // 각각의 탭 패널 생성
        JPanel exercisePanel = this.createExerciseTab(bgColor, btnColor); // 운동 기록 조회 탭 생성
        JPanel goalPanel = this.createGoalTab(bgColor, btnColor); // 금일 목표 조회 탭 생성
        JPanel healthPanel = this.createHealthTab(bgColor, btnColor); // 건강 상태 조회 탭 생성

        // 탭 패널에 탭
        tabbedPane.addTab("운동 기록 조회", exercisePanel);
        tabbedPane.addTab("금일 목표 조회", goalPanel);
        tabbedPane.addTab("건강 상태 조회", healthPanel);

        // 뒤로가기 버튼 생성 및 이벤트 리스너 추가
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener((e) -> {
            mainPage.showMainPage(); // 메인 페이지로 돌아가는 메서드 호출
        });
        backButton.setBackground(btnColor); // 버튼 배경색 설정

        // 컴포넌트 배치
        this.add(tabbedPane, "Center"); // 중앙에 탭 패널 추가
        this.add(backButton, "South"); // 하단에 뒤로가기 버튼 추가
    }

    // 운동 기록 조회 탭
    private JPanel createExerciseTab(Color bgColor, Color btnColor) {
        // 패널 및 레이아웃 구성
        JPanel exercisePanel = new JPanel(new BorderLayout());
        JPanel exerciseSearchPanel = new JPanel(new GridLayout(4, 4, 5, 5)); // 3행 4열로 변경 (시작 날짜, 종료 날짜 추가)

        // 검색 필드 및 체크박스 생성
        JTextField userIdField = new JTextField();
        JTextField startDateField = new JTextField(); // 시작 날짜 입력 필드
        JTextField endDateField = new JTextField(); // 종료 날짜 입력 필드
        JTextField exerciseTypeField = new JTextField();
        JTextField calorieField = new JTextField();
        JCheckBox greaterThanCheckBox = new JCheckBox("이상");
        JCheckBox lessThanCheckBox = new JCheckBox("이하");
        JButton searchButton = new JButton("운동 기록 조회");

        // 체크박스 간 상호 배타적 선택 설정
        greaterThanCheckBox.addActionListener(e -> {
            if (greaterThanCheckBox.isSelected()) lessThanCheckBox.setSelected(false);
        });
        lessThanCheckBox.addActionListener(e -> {
            if (lessThanCheckBox.isSelected()) greaterThanCheckBox.setSelected(false);
        });

        // 검색 패널에 컴포넌트 추가
        exerciseSearchPanel.add(new JLabel("사용자 ID:"));
        exerciseSearchPanel.add(userIdField);
        exerciseSearchPanel.add(new JLabel("운동 종류:"));
        exerciseSearchPanel.add(exerciseTypeField);
        exerciseSearchPanel.add(new JLabel("시작 날짜:"));
        exerciseSearchPanel.add(startDateField); // 시작 날짜 필드
        exerciseSearchPanel.add(new JLabel("종료 날짜:"));
        exerciseSearchPanel.add(endDateField); // 종료 날짜 필드
        exerciseSearchPanel.add(new JLabel("칼로리 소비량:"));
        exerciseSearchPanel.add(calorieField);
        exerciseSearchPanel.add(greaterThanCheckBox); // 이상 체크박스
        exerciseSearchPanel.add(lessThanCheckBox); // 이하 체크박스
        exerciseSearchPanel.add(new JLabel("")); // 빈 자리 추가
        exerciseSearchPanel.add(new JLabel("")); // 빈 자리 추가
        exerciseSearchPanel.add(new JLabel("")); // 빈 자리 추가
        exerciseSearchPanel.add(searchButton); // 검색 버튼

        // 테이블 및 스크롤 생성
        this.exerciseTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(this.exerciseTable);

        // 패널에 컴포넌트 추가
        exercisePanel.add(exerciseSearchPanel, "North");
        exercisePanel.add(scrollPane, "Center");
        exercisePanel.setBackground(bgColor);
        searchButton.setBackground(btnColor);

        // 검색 버튼 이벤트 리스너
        searchButton.addActionListener((e) -> {
            DB_Conn_Query dbConnQuery = new DB_Conn_Query();
            Connection connection = dbConnQuery.getConnection();
            if (connection != null) {
                try {
                    exerciseSearchDAO dao = new exerciseSearchDAO(connection);
                    Integer calorieAmount = null;
                    boolean isGreaterThan = greaterThanCheckBox.isSelected();
                    boolean isLessThan = lessThanCheckBox.isSelected();

                    // 칼로리 값이 입력된 경우 파싱
                    if (!calorieField.getText().isEmpty()) {
                        calorieAmount = Integer.parseInt(calorieField.getText());
                    }

                    // 칼로리 소비량에 대해서 체크박스가 선택되지 않으면 기본적으로 '이하' 조건 적용
                    if (!greaterThanCheckBox.isSelected() && !lessThanCheckBox.isSelected()) {
                        isLessThan = true; // 기본값으로 이하 조건 설정
                    }

                    // 검색 결과 조회
                    List<exerciseDTO> results = dao.getExerciseRecordsWithDateRange(
                            userIdField.getText(),
                            startDateField.getText(),
                            endDateField.getText(), // 시작 날짜와 종료 날짜 추가
                            exerciseTypeField.getText(),
                            calorieAmount,
                            isGreaterThan, isLessThan
                    );

                    // 테이블 업데이트
                    this.updateTable(this.exerciseTable, results, "운동기록삭제");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "운동 기록 조회 중 오류 발생: " + ex.getMessage());
                } finally {
                    dbConnQuery.closeConnection();
                }
            }
        });

        return exercisePanel;
    }

    // 금일 목표 조회 탭
    private JPanel createGoalTab(Color bgColor, Color btnColor) {
        // 패널 및 레이아웃 설정
        JPanel goalPanel = new JPanel(new BorderLayout()); // 메인 패널 (BorderLayout 사용)
        JPanel goalSearchPanel = new JPanel(new GridLayout(2, 5, 5, 5)); // 검색 영역 패널 (GridLayout 사용)

        // 입력 필드 및 버튼 생성
        JTextField userIdField = new JTextField(); // 사용자 ID 입력 필드
        JTextField dateField = new JTextField(); // 날짜 입력 필드
        JTextField calorieField = new JTextField(); // 목표 칼로리 소비량 입력 필드
        JCheckBox greaterThanCheckBox = new JCheckBox("이상"); // 칼로리 이상 체크박스
        JCheckBox lessThanCheckBox = new JCheckBox("이하"); // 칼로리 이하 체크박스
        JButton searchButton = new JButton("금일 목표 조회"); // 검색 버튼

        // 체크박스 간 상호 배타적 선택 설정
        greaterThanCheckBox.addActionListener(e -> {
            if (greaterThanCheckBox.isSelected()) lessThanCheckBox.setSelected(false);
        });
        lessThanCheckBox.addActionListener(e -> {
            if (lessThanCheckBox.isSelected()) greaterThanCheckBox.setSelected(false);
        });

        // 검색 패널에 컴포넌트 추가
        goalSearchPanel.add(new JLabel("사용자 ID:")); // 사용자 ID 라벨 추가
        goalSearchPanel.add(userIdField); // 사용자 ID 입력 필드 추가
        goalSearchPanel.add(new JLabel("날짜:")); // 날짜 라벨 추가
        goalSearchPanel.add(dateField); // 날짜 입력 필드 추가
        goalSearchPanel.add(new JLabel("")); // 빈 자리 추가
        goalSearchPanel.add(new JLabel("목표 칼로리 소비량:")); // 목표 칼로리 소비량 라벨 추가
        goalSearchPanel.add(calorieField); // 목표 칼로리 소비량 입력 필드 추가
        goalSearchPanel.add(greaterThanCheckBox); // 칼로리 이상 체크박스 추가
        goalSearchPanel.add(lessThanCheckBox); // 칼로리 이하 체크박스 추가
        goalSearchPanel.add(searchButton); // 검색 버튼 추가

        // 테이블 및 스크롤 패널 생성
        this.goalTable = new JTable(); // 목표 조회 결과를 표시할 테이블
        JScrollPane scrollPane = new JScrollPane(this.goalTable); // 테이블에 스크롤 추가

        // 패널 구성
        goalPanel.add(goalSearchPanel, "North"); // 검색 영역을 상단에 추가
        goalPanel.add(scrollPane, "Center"); // 테이블을 중앙에 추가
        goalPanel.setBackground(bgColor); // 패널 배경색 설정
        searchButton.setBackground(btnColor); // 버튼 배경색 설정

        // 검색 버튼에 이벤트 리스너 추가
        searchButton.addActionListener((e) -> {
            DB_Conn_Query dbConnQuery = new DB_Conn_Query(); // DB 연결 객체 생성
            Connection connection = dbConnQuery.getConnection(); // 데이터베이스 연결 가져오기
            if (connection != null) {
                try {
                    // 데이터 조회 DAO 객체 생성
                    dailyGoalSearchDAO dao = new dailyGoalSearchDAO(connection);

                    Integer calorieAmount = null;
                    boolean isGreaterThan = greaterThanCheckBox.isSelected();

                    // 목표 칼로리 소비량이 입력된 경우 파싱
                    if (!calorieField.getText().isEmpty()) {
                        calorieAmount = Integer.parseInt(calorieField.getText());
                    }

                    // 만약 이상/이하 체크박스가 모두 선택되지 않았다면, 이하 조건을 기본값으로 설정
                    if (!greaterThanCheckBox.isSelected() && !lessThanCheckBox.isSelected()) {
                        lessThanCheckBox.setSelected(true); // 기본값으로 이하 체크박스 선택
                        isGreaterThan = false; // 이하 조건을 기본값으로 설정
                    }

                    // 사용자 입력값으로 금일 목표 조회
                    List<dailyGoalDTO> results = dao.getDailyGoals(
                            userIdField.getText(), // 사용자 ID
                            dateField.getText(), // 날짜
                            calorieAmount, // 목표 칼로리 소비량
                            isGreaterThan // 이상/이하 조건
                    );

                    // 테이블 업데이트
                    this.updateTable(this.goalTable, results, "목표기록삭제");
                } catch (Exception ex) {
                    // 예외 처리 및 에러 메시지 출력
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "금일 목표 조회 중 오류 발생: " + ex.getMessage());
                } finally {
                    // 데이터베이스 연결 종료
                    dbConnQuery.closeConnection();
                }
            }
        });
        return goalPanel; // 구성된 금일 목표 조회 패널 반환
    }

    // 건강 상태 기록 조회 탭
    private JPanel createHealthTab(Color bgColor, Color btnColor) {
        // 패널 초기화 및 레이아웃 설정
        JPanel healthPanel = new JPanel(new BorderLayout());
        JPanel healthSearchPanel = new JPanel(new GridLayout(4, 4, 5, 5)); // 4행 4열로 설정 (마지막 행에 버튼을 배치)

        // 검색 필드와 버튼 생성
        JTextField userIdField = new JTextField(); // 사용자 ID 입력 필드
        JTextField startDateField = new JTextField(); // 시작 날짜 입력 필드
        JTextField endDateField = new JTextField(); // 종료 날짜 입력 필드
        JComboBox<String> achievementStatusComboBox = new JComboBox<>(new String[]{"전체", "달성", "미달성", "분발필요"}); // 목표 달성 여부 선택
        JTextField avgCalorieField = new JTextField(); // 평균 대비 칼로리 소비량 입력 필드
        JCheckBox aboveCheckBox = new JCheckBox("이상"); // 이상 체크박스
        JCheckBox belowCheckBox = new JCheckBox("이하"); // 이하 체크박스
        JButton searchButton = new JButton("건강 상태 조회"); // 조회 버튼

        // 검색 필드를 검색 패널에 추가
        healthSearchPanel.add(new JLabel("사용자 ID:"));
        healthSearchPanel.add(userIdField);
        healthSearchPanel.add(new JLabel("목표 달성 여부:"));
        healthSearchPanel.add(achievementStatusComboBox);

        healthSearchPanel.add(new JLabel("시작 날짜:"));
        healthSearchPanel.add(startDateField);
        healthSearchPanel.add(new JLabel("종료 날짜:"));
        healthSearchPanel.add(endDateField);

        healthSearchPanel.add(new JLabel("평균 대비 소비량:"));
        healthSearchPanel.add(avgCalorieField);
        healthSearchPanel.add(aboveCheckBox); // 이상 체크박스
        healthSearchPanel.add(belowCheckBox); // 이하 체크박스

        healthSearchPanel.add(new JLabel("")); // 빈 자리 추가
        healthSearchPanel.add(new JLabel("")); // 빈 자리 추가
        healthSearchPanel.add(new JLabel("")); // 빈 자리 추가
        healthSearchPanel.add(searchButton); // 조회 버튼

        // 결과를 보여줄 테이블과 스크롤 패널 추가
        this.healthTable = new JTable(); // 데이터 표시용 테이블
        JScrollPane scrollPane = new JScrollPane(this.healthTable);
        healthPanel.add(healthSearchPanel, BorderLayout.NORTH);
        healthPanel.add(scrollPane, BorderLayout.CENTER);
        healthPanel.setBackground(bgColor); // 배경색 설정
        searchButton.setBackground(btnColor); // 버튼 색상 설정

        // 조회 버튼 클릭 이벤트 처리
        searchButton.addActionListener((e) -> {
            DB_Conn_Query dbConnQuery = new DB_Conn_Query();
            Connection connection = dbConnQuery.getConnection(); // DB 연결 초기화

            if (connection != null) {
                try {
                    // DAO 인스턴스 생성
                    healthStatusSearchDAO dao = new healthStatusSearchDAO(connection);

                    // 입력된 값 가져오기
                    String userId = userIdField.getText();
                    String startDate = startDateField.getText();
                    String endDate = endDateField.getText();
                    String selectedStatus = (String) achievementStatusComboBox.getSelectedItem();
                    String avgCalorieInput = avgCalorieField.getText();
                    Integer avgCalorieAmount = avgCalorieInput.isEmpty() ? null : Integer.parseInt(avgCalorieInput);

                    // 이상/이하 조건 체크
                    boolean isGreaterThan = aboveCheckBox.isSelected();
                    boolean isLessThan = belowCheckBox.isSelected();

                    // "전체" 선택 시 null로 설정
                    if ("전체".equals(selectedStatus)) {
                        selectedStatus = null;
                    }

                    // 칼로리 소비량에 대해 체크박스가 선택되지 않으면 기본적으로 '이하' 조건 적용
                    if (!aboveCheckBox.isSelected() && !belowCheckBox.isSelected()) {
                        isLessThan = true; // 기본값으로 이하 조건 설정
                    }

                    // DAO 호출하여 데이터 가져오기
                    List<healthStatusDTO> results = dao.getHealthRecordsWithDateRange(
                            userId,
                            startDate,
                            endDate,
                            selectedStatus,
                            avgCalorieAmount,
                            isGreaterThan,
                            isLessThan
                    );

                    // 테이블 업데이트
                    this.updateTable(this.healthTable, results, "건강기록삭제");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "건강 상태 조회 중 오류 발생: " + ex.getMessage());
                } finally {
                    dbConnQuery.closeConnection(); // DB 연결 종료
                }
            }
        });

        return healthPanel; // 완성된 패널 반환
    }

    // 데이터 테이블 업데이트
    private <T> void updateTable(JTable table, List<T> results, String deleteButtonName) {
        // 결과 리스트가 null이 아니고 비어 있지 않을 경우 실행
        if (results != null && !results.isEmpty()) {
            DefaultTableModel model; // 테이블 모델
            String[] columnNames; // 컬럼 이름 배열
            Object[][] data; // 테이블 데이터 배열

            // 결과 리스트의 첫 번째 객체가 exerciseDTO일 경우
            if (results.get(0) instanceof exerciseDTO) {
                columnNames = new String[]{
                        "운동 기록 번호", "사용자 ID", "날짜", "운동 종류", "운동 시간", "칼로리 소비량", deleteButtonName
                }; // 컬럼 이름 설정

                // 데이터 변환: exerciseDTO 리스트를 Object 배열로 매핑
                data = results.stream().map(r -> {
                    exerciseDTO dto = (exerciseDTO) r;
                    return new Object[]{
                            dto.getRecordId(), dto.getUserId(), dto.getDate(),
                            dto.getExerciseType(), dto.getDuration(),
                            dto.getCalories(), deleteButtonName
                    };
                }).toArray(Object[][]::new);

                model = new DefaultTableModel(data, columnNames); // 테이블 모델 생성

            } else if (results.get(0) instanceof dailyGoalDTO) { // dailyGoalDTO 처리
                columnNames = new String[]{
                        "목표 번호", "사용자 ID", "날짜", "목표 칼로리 소비량", deleteButtonName
                }; // 컬럼 이름 설정

                // 데이터 변환: dailyGoalDTO 리스트를 Object 배열로 매핑
                data = results.stream().map(r -> {
                    dailyGoalDTO dto = (dailyGoalDTO) r;
                    return new Object[]{
                            dto.getGoalId(), dto.getUserId(), dto.getDate(),
                            dto.getCalorieGoal(), deleteButtonName
                    };
                }).toArray(Object[][]::new);

                model = new DefaultTableModel(data, columnNames); // 테이블 모델 생성

            } else { // healthStatusDTO 처리
                if (!(results.get(0) instanceof healthStatusDTO)) { // 잘못된 데이터 타입인 경우 반환
                    return;
                }

                columnNames = new String[]{
                        "건강 기록 번호", "사용자 ID", "날짜", "총 칼로리 소비량", "목표 달성률",
                        "목표 달성 여부", "평균 칼로리 소비량", "평균 대비 칼로리 소비량", deleteButtonName
                }; // 컬럼 이름 설정

                // 데이터 변환: healthStatusDTO 리스트를 Object 배열로 매핑
                data = results.stream().map(r -> {
                    healthStatusDTO dto = (healthStatusDTO) r;
                    return new Object[]{
                            dto.getHealthRecordId(), dto.getUserId(), dto.getDate(),
                            dto.getTotalCalories(), dto.getAchievementRate(),
                            dto.getAchievementStatus(), dto.getAverageCalories(),
                            dto.getCaloriesComparedToAverage(), deleteButtonName
                    };
                }).toArray(Object[][]::new);

                model = new DefaultTableModel(data, columnNames); // 테이블 모델 생성
            }

            // JTable에 새 모델 설정
            table.setModel(model);

            // 삭제 버튼 컬럼에 버튼 렌더러 및 편집기 설정
            table.getColumn(deleteButtonName).setCellRenderer(new ButtonRenderer(deleteButtonName));
            table.getColumn(deleteButtonName).setCellEditor(new ButtonEditor(new JCheckBox(), table, deleteButtonName));

        } else {
            // 결과가 비어 있는 경우 사용자에게 메시지 표시
            JOptionPane.showMessageDialog(this, "조건에 맞는 데이터가 없습니다.");
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String deleteButtonName) {
            this.setText("삭제"); // 버튼의 텍스트 설정
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this; // JButton 자체를 반환하여 버튼을 렌더링
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JTable table; // 현재 JTable 객체
        private JButton button; // 셀에 표시될 버튼
        private String buttonName; // 버튼의 이름 (삭제 기능 구분용)

        public ButtonEditor(JCheckBox checkBox, JTable table, String buttonName) {
            super(checkBox);
            this.table = table;
            this.buttonName = buttonName;
            this.button = new JButton(buttonName); // 버튼 생성
            this.button.addActionListener(e -> this.deleteRow()); // 버튼 클릭 시 삭제 처리
        }

        private void deleteRow() {
            int rowToDelete = this.table.getSelectedRow(); // 삭제할 행 인덱스 가져오기
            if (rowToDelete != -1) {
                // 첫 번째 열의 값을 삭제 ID로 간주
                int idToDelete = (Integer) this.table.getValueAt(rowToDelete, 0);
                int confirmation = JOptionPane.showConfirmDialog(
                        this.table, "정말로 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION
                );

                // 사용자가 "예"를 선택한 경우 삭제 처리
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        DB_Conn_Query dbConnQuery = new DB_Conn_Query();
                        Connection connection = dbConnQuery.getConnection();

                        if (connection != null) {
                            boolean isDeleted;

                            // 버튼 이름에 따라 DAO를 호출하여 삭제 처리
                            if (this.buttonName.equals("목표기록삭제")) {
                                dailyGoalDeleteDAO deleteDAO = new dailyGoalDeleteDAO(connection);
                                isDeleted = deleteDAO.deleteGoal(idToDelete);
                            } else if (this.buttonName.equals("운동기록삭제")) {
                                ExerciseRecordDeleteDAO deleteDAO = new ExerciseRecordDeleteDAO(connection);
                                isDeleted = deleteDAO.deleteExerciseRecord(idToDelete);
                            } else if (this.buttonName.equals("건강기록삭제")) {
                                HealthStatusRecordDeleteDAO deleteDAO = new HealthStatusRecordDeleteDAO(connection);
                                isDeleted = deleteDAO.deleteHealthStatusRecord(idToDelete);
                            } else {
                                JOptionPane.showMessageDialog(this.table, "알 수 없는 삭제 요청입니다.");
                                return;
                            }

                            // 삭제 성공 여부에 따라 메시지 표시 및 행 삭제
                            if (isDeleted) {
                                ((DefaultTableModel) this.table.getModel()).removeRow(rowToDelete);
                                JOptionPane.showMessageDialog(this.table, "기록이 삭제되었습니다.");
                            } else {
                                JOptionPane.showMessageDialog(this.table, "삭제에 실패했습니다.");
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this.table, "삭제 중 오류 발생: " + ex.getMessage());
                    }
                }
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return this.button; // JButton 반환
        }
    }
}