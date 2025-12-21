package fopassignment;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CalendarView extends VBox {

    public CalendarView() {

        Label title = new Label("View Calendar");

        ComboBox<String> monthBox = new ComboBox<>();
        monthBox.getItems().addAll(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        );
        monthBox.setPromptText("Month");

        ComboBox<Integer> yearBox = new ComboBox<>();
        for (int year = 2024; year <= 2030; year++) {
            yearBox.getItems().add(year);
        }
        yearBox.setPromptText("Year");

        HBox selectorBox = new HBox(10, monthBox, yearBox);

        Button showBtn = new Button("Show Calendar");
        Button backBtn = new Button("Back");

        TextArea calendarArea = new TextArea();
        calendarArea.setEditable(false);
        calendarArea.setPromptText("Calendar will appear here");
        calendarArea.setPrefHeight(200);

        showBtn.setOnAction(e -> {
            calendarArea.setText("Calendar logic not connected yet");
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
