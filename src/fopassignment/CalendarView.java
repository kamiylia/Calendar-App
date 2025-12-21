package fopassignment;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CalendarView extends VBox {

    public CalendarView() {

        Label label = new Label("Calendar View (Coming Soon)");

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> FxMain.showMainMenu());

        setSpacing(15);
        setStyle("-fx-padding: 20;");
        getChildren().addAll(label, backBtn);
    }
}
