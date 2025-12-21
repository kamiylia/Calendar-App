package fopassignment;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SearchView extends VBox {

    private TextField inputField;
    private TextArea resultArea;

    public SearchView() {

        Label title = new Label("Search Event");
        title.getStyleClass().add("title");


        inputField = new TextField();
        inputField.setPromptText("Enter date (YYYY-MM-DD) or keyword");

        Button searchBtn = new Button("Search");
        Button backBtn = new Button("Back");

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPromptText("Search results will appear here");

        searchBtn.setOnAction(e -> {
            resultArea.setText("Waiting for logic connection...");
        });

        backBtn.setOnAction(e -> FxMain.showMainMenu());

        setSpacing(10);
        setStyle("-fx-padding: 20;");
        getChildren().addAll(
            title,
            inputField,
            searchBtn,
            resultArea,
            backBtn
        );
    }
}
