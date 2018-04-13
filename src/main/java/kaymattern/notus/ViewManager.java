package kaymattern.notus;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class handles the views.
 */
public class ViewManager {

    private App app;
    private Stage stage;
    private Map<View, CachedView> sceneCache = new HashMap<>();

    /**
     * Constructor.
     * @param stage The stage to show the views
     */
    protected ViewManager(App app, Stage stage) {
        this.app = app;
        this.stage = stage;
    }

    /**
     * Shows the given view.
     * The view is shown in the primary stage or in a new stage if it is a dialog.
     * @param view The view to show
     */
    protected void showView(View view) {
        CachedView cachedView = getView(view);

        cachedView.getController().entered();

        Scene scene = cachedView.getScene();
        String title = view.getDisplayName();

        if (view.isDialog()) {
            showDialog(scene, title);
        } else {
            double width = this.stage.getWidth();
            double height = this.stage.getHeight();
            this.stage.setScene(scene);
            this.stage.setTitle(title);

            // restore previous window size
            this.stage.setWidth(width);
            this.stage.setHeight(height);
        }
    }

    /**
     * Retrieve the controller of a view.
     * @param view The view of which to get the controller.
     * @return The controller
     */
    protected NotusController getControllerOfView(View view) {
        return getView(view).getController();
    }

    /**
     * Show a dialog on the main stage.
     * @param alertType The type of the alert.
     * @param title The title of the alert.
     * @param headerText The header text of the alert.
     * @param contentText The content of the alert.
     */
    protected boolean showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initOwner(this.stage);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    /**
     * Shows the given scene in a dialog.
     * @param scene The scene to show in the dialog
     */
    private void showDialog(Scene scene, String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(this.stage);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    /**
     * Get the scene of the corresponding view.
     * The scenes are only loaded the first time and then cached.
     * @param view The view
     * @return The scene of the view
     */
    private CachedView getView(View view) {
        if (! sceneCache.containsKey(view)) {
            sceneCache.put(view, loadView(view));
        }
        return sceneCache.get(view);
    }

    /**
     * Load the scene of the corresponding view.
     * @param view The view
     * @return The loaded scene
     */
    private CachedView loadView(View view) {
        String viewFile = String.format("kaymattern/views/%s%s.fxml",
                view.isDialog() ? "dialogs/" : "",
                view.getName());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(viewFile));
            Parent parent = loader.load();
            NotusController controller = loader.getController();
            controller.setUp(this.app);
            Scene scene = new Scene(parent);
            return new CachedView(scene, controller);
        } catch (IOException e) {
            this.app.showAlert(Alert.AlertType.ERROR, "Error", "Ein Fehler ist aufgetreten: Die View kann nicht angezeigt werden.", "Das Programm wird beendet.");
            e.printStackTrace();
            Platform.exit();
            return null;
        }
    }

    /**
     * Container object that holds a loaded view.
     * It has the scene and the controller of the view.
     */
    private class CachedView {

        private final Scene scene;
        private final NotusController controller;

        CachedView(Scene scene, NotusController controller) {
            this.scene = scene;
            this.controller = controller;
        }

        Scene getScene() {
            return scene;
        }

        NotusController getController() {
            return controller;
        }

    }

}
