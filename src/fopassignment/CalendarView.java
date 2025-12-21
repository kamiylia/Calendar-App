package fopassignment;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CalendarView extends VBox {

    public CalendarView() {

        // Title
        Label title = new Label("View Calendar");
        title.getStyleClass().add("title");

        // Month dropdown
        ComboBox<String> monthBox = new ComboBox<>();
        monthBox.getItems().addAll(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        );
        monthBox.setPromptText("Select Month");

        // Year dropdown (ONLY 2025 & 2026)
        ComboBox<Integer> yearBox = new ComboBox<>();
        yearBox.getItems().addAll(2025, 2026);
        yearBox.setPromptText("Select Year");

        // Put dropdowns side by side
        HBox dropdownBox = new HBox(10, monthBox, yearBox);

        // Buttons
        Button showBtn = new Button("Show Calendar");
        Button backBtn = new Button("Back");

        // Display area (UI placeholder)
        TextArea calendarArea = new TextArea();
        calendarArea.setEditable(false);
        calendarArea.setPromptText("Calendar will be displayed here");
        calendarArea.setPrefHeight(200);

        // Temporary UI-only action
        showBtn.setOnAction(e -> {
            calendarArea.setText(
                "Selected:\nMonth: " + monthBox.getValue() +
                "\nYear: " + yearBox.getValue() +
                "\n\n(Calendar logic will be connected later)"
            );
        });

        backBtn.setOnAction(e -> FxMain.showMainMenu());

        // Layout settings
        setSpacing(12);
        setStyle("-fx-padding: 20;");
        getChildren().addAll(
            title,
            dropdownBox,
            showBtn,
            calendarArea,
            backBtn
        );
    }
}
