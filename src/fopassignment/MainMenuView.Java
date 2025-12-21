package fopassignment;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuView extends VBox {

    public MainMenuView() {

        Button searchBtn = new Button("Search Event");
        Button calendarBtn = new Button("View Calendar");
        Button exitBtn = new Button("Exit");

        searchBtn.setOnAction(e -> FxMain.showSearchView());
        calendarBtn.setOnAction(e -> FxMain.showCalendarView());
        exitBtn.setOnAction(e -> System.exit(0));

        setSpacing(15);
        setStyle("-fx-padding: 20;");
        getChildren().addAll(searchBtn, calendarBtn, exitBtn);
    }
}
