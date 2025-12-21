package fopassignment;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CalendarView extends VBox {

    private ComboBox<String> monthBox;
    private ComboBox<Integer> yearBox;
    private TextArea calendarArea;

    public CalendarView() {

        Label title = new Label("View Calendar");

        // Month selector
        monthBox = new ComboBox<>();
        monthBox.getItems().addAll(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        );
        monthBox.setPromptText("Month");

        // Year selector
        yearBox = new ComboBox<>();
        for (int year = 2024; year <= 2030; year++) {
            yearBox.getItems().add(year);
        }
        yearBox.setPromptText("Year");

        HBox selectorBox = new HBox(10, monthBox, yearBox);

        Button showBtn = new Button("Show Calendar");
        Button backBtn = new Button("Back");

        calendarArea = new TextArea();
        calendarArea.setEditable(false);
        calendarArea.setPromptText("Calendar will be displayed here");
        calendarArea.setPrefHeight(200);

        showBtn.setOnAction(e -> {
            calendarArea.setText("Calendar display not connected yet");
        });

        backBtn.setOnAction(e -> FxMain.showMainMenu());

        setSpacing(12);
        setStyle("-fx-padding: 20;");
        getChildren().addAll(
            title,
            selectorBox,
            showBtn,
            calendarArea,
            backBtn
        );
    }
}
