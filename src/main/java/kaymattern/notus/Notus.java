package kaymattern.notus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kaymattern.notus.database.DataAccessor;
import kaymattern.notus.model.Subject;

import java.util.Arrays;

/**
 * Main class of the Notus application.
 *
 * @author Kay Mattern
 */
public class Notus extends Application implements App {

    private DataAccessor db;
    private ViewManager viewManager;

    /**
     * Main entry point of the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Constructor.
     * Called before {@link Notus#start(Stage)}.
     */
    public Notus() {
        this.db = new DataAccessor();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Notus");

        this.viewManager = new ViewManager(primaryStage);
        this.viewManager.showView(ViewManager.View.SUBJECT_OVERVIEW);

        primaryStage.show();
    }

    @Override
    public void showView(ViewManager.View view) {
        this.viewManager.showView(view);
    }

    @Override
    public DataAccessor getDataAccessor() {
        return this.db;
    }
}
