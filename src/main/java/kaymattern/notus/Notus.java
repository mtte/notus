package kaymattern.notus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the Notus application.
 */
public class Notus extends Application {

    /**
     * Main entry point of the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("kaymattern/views/SubjectOverview.fxml"));
        Parent subjectOverview = loader.load();
        primaryStage.setScene(new Scene(subjectOverview));
        primaryStage.setTitle("Notus");
        primaryStage.show();
    }
}
