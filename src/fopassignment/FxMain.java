package fopassignment;

import javafx.application.Application;
import javafx.scene.Scene;
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

    public static void showMainMenu() {
        Scene scene = new Scene(new MainMenuView(), 400, 300);
        mainStage.setScene(scene);
    }

    public static void showSearchView() {
        Scene scene = new Scene(new SearchView(), 400, 300);
        mainStage.setScene(scene);
    }

    public static void showCalendarView() {
        Scene scene = new Scene(new CalendarView(), 400, 300);
        mainStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
