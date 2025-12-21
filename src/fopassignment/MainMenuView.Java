package fopassignment;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuView extends VBox {

    public MainMenuView() {

        Button searchBtn = new Button("Search Event");
        Button calendarBtn = new Button("View Calendar");
        Button exitBtn = new Button("Exit");

        setSpacing(20);
        setPrefSize(400, 300);
        setStyle("-fx-padding: 20;");

        getChildren().addAll(searchBtn, calendarBtn, exitBtn);
    }
}
