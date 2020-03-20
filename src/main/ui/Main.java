package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.scenes.MainListScene;

// Starts application
public class Main extends Application {
    //Effects: begins the tracker app
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        new MainListScene();
    }
}
