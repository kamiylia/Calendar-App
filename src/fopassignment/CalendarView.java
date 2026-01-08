package fopassignment;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class CalendarView extends VBox {

    private final GridPane calendarGrid;
    private final ComboBox<String> monthBox;
    private final ComboBox<Integer> yearBox;

    public CalendarView() {

        setSpacing(15);
        setStyle("-fx-padding: 20;");

        // ===== Title =====
        Label title = new Label("View Calendar");
        title.setFont(Font.font(22));

        // ===== Month Dropdown =====
        monthBox = new ComboBox<>();
        monthBox.getItems().addAll(
            "January","February","March","April","May","June",
            "July","August","September","October","November","December"
        );

        // ===== Year Dropdown (LIMITED) =====
        yearBox = new ComboBox<>();
        yearBox.getItems().addAll(2025, 2026);

        // Set current month/year as default
        LocalDate today = LocalDate.now();
        monthBox.getSelectionModel().select(today.getMonthValue() - 1);
        yearBox.setValue(
            yearBox.getItems().contains(today.getYear()) ? today.getYear() : 2025
        );

        Button showBtn = new Button("Show Calendar");

        HBox controls = new HBox(10, monthBox, yearBox, showBtn);
        controls.setAlignment(Pos.CENTER_LEFT);

        // ===== Calendar Grid =====
        calendarGrid = new GridPane();
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.setGridLinesVisible(true);

        VBox.setVgrow(calendarGrid, Priority.ALWAYS);

        // ===== Back Button =====
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> FxMain.showMainMenu());

        // ===== Button Action =====
        showBtn.setOnAction(e -> {
            int month = monthBox.getSelectionModel().getSelectedIndex() + 1;
            int year = yearBox.getValue();
            drawCalendar(month, year);
        });

        getChildren().addAll(title, controls, calendarGrid, backBtn);

        // Draw initial calendar
        drawCalendar(
            monthBox.getSelectionModel().getSelectedIndex() + 1,
            yearBox.getValue()
        );
    }

    // ===== Calendar Logic =====
    private void drawCalendar(int month, int year) {
        calendarGrid.getChildren().clear();

        String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

        // Day headers
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold;");
            calendarGrid.add(dayLabel, i, 0);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDay = yearMonth.atDay(1);

        int startCol = firstDay.getDayOfWeek().getValue() % 7;
        int daysInMonth = yearMonth.lengthOfMonth();

        int row = 1;
        int col = startCol;

        for (int date = 1; date <= daysInMonth; date++) {
            Label dayCell = new Label(String.valueOf(date));
            dayCell.setMinSize(50, 50);
            dayCell.setAlignment(Pos.CENTER);
            dayCell.setStyle(
                "-fx-border-color: lightgray; -fx-font-size: 14;"
            );

            calendarGrid.add(dayCell, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }
}
