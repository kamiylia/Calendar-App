package fopassignment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FxMain extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        showMainMenu();
        stage.setTitle("Calendar App");
        stage.show();
    }

    // ===== SCREEN 1: MAIN MENU =====
    static void showMainMenu() {
        Button searchBtn = new Button("Search Event");
        Button calendarBtn = new Button("View Calendar");
        Button exitBtn = new Button("Exit");

        searchBtn.setOnAction(e -> showSearchView());
        calendarBtn.setOnAction(e -> showCalendarView());
        exitBtn.setOnAction(e -> System.exit(0));

        VBox root = new VBox(20, searchBtn, calendarBtn, exitBtn);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 400, 300);
        applyStyles(scene);
        mainStage.setScene(scene);

    }

    // ===== SCREEN 2: SEARCH VIEW =====
    static void showSearchView() {
        Label label = new Label("Search View (Coming Soon)");
        Button backBtn = new Button("Back");

        backBtn.setOnAction(e -> showMainMenu());

        VBox root = new VBox(20, label, backBtn);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 400, 300);
        applyStyles(scene);
        mainStage.setScene(scene);
    }

    // ===== SCREEN 3: CALENDAR VIEW =====
    static void showCalendarView() {
        VBox root = new VBox(20);
        root.getChildren().add(new Label("Calendar View"));
    
        Scene scene = new Scene(root, 400, 300);
        applyStyles(scene);
        mainStage.setScene(scene);
    }
    

    private static void applyStyles(Scene scene) {
        scene.getStylesheets().add(
            FxMain.class.getResource("/fopassignment/styles/app.css").toExternalForm()
        );
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}
