package fopassignment;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SearchView extends VBox {

    public SearchView() {

        Label title = new Label("Search Event");

        TextField input = new TextField();
        input.setPromptText("Enter date or keyword");

        Button searchBtn = new Button("Search");
        Button backBtn = new Button("Back");

        Label resultLabel = new Label("Results will appear here");

        searchBtn.setOnAction(e -> {
            resultLabel.setText("Search clicked (logic not connected yet)");
        });

        backBtn.setOnAction(e -> FxMain.showMainMenu());

        setSpacing(10);
        setStyle("-fx-padding: 20;");
        getChildren().addAll(title, input, searchBtn, resultLabel, backBtn);
    }
}
