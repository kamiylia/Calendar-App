package fopassignment;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;


public class MainMenuView extends VBox {

    public MainMenuView() {
        setSpacing(15);
        setAlignment(Pos.CENTER);
        setStyle("-fx-padding: 20;");

        Button calendarBtn = new Button("View Calendar");
        Button exitBtn = new Button("Exit");

        calendarBtn.setOnAction(e -> FxMain.showCalendarView());
        exitBtn.setOnAction(e -> System.exit(0));

        getChildren().addAll(calendarBtn, exitBtn);
    }
}
