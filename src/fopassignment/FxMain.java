package fopassignment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxMain extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        mainStage.setTitle("Calendar and Scheduler App");
        showMainMenu();
        mainStage.show();
    }

    public static void showMainMenu() {
        MainMenuView view = new MainMenuView();
        Scene scene = new Scene(view, 600, 500);
        mainStage.setScene(scene);
    }

    public static void showCalendarView() {
        CalendarView view = new CalendarView();
        Scene scene = new Scene(view, 700, 550);
        mainStage.setScene(scene);
    }

    public static Stage getStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
