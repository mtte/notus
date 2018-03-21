package kaymattern.notus;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Main class of the Notus application.
 */
public class Notus extends Application {

    /**
     * Main entry point of the application.
     */
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
    }
}
