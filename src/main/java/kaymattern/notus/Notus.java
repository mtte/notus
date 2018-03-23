package kaymattern.notus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kaymattern.notus.database.DataAccessor;

import java.util.Arrays;

/**
 * Main class of the Notus application.
 */
public class Notus extends Application {

    private DataAccessor db;

    /**
     * Main entry point of the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    public Notus() {
        this.db = new DataAccessor();
        db.loadData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("kaymattern/views/SubjectOverview.fxml"));
        Parent subjectOverview = loader.load();
        primaryStage.setScene(new Scene(subjectOverview));
        primaryStage.setTitle("Notus");
        primaryStage.show();


    }

    private void printRS(Object[][] res) {
        System.out.println("\nResult:");
        Arrays.stream(res).map(Arrays::asList).forEach(System.out::println);
    }

}
