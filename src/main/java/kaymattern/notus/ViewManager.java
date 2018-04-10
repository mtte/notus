package kaymattern.notus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public void showView(View view) {
        if (view.isDialog()) {
            showDialog(view);
        } else {
            this.stage.setScene(getView(view).getScene());
            this.stage.setTitle(view.getDisplayName());
        }
    }

    public NotusController getControllerOfView(View view) {
        return getView(view).getController();
    }

    /**
     * Shows the given view in a dialog.
     * @param view The view to show in the dialog
     */
    private void showDialog(View view) {
        Scene scene = getView(view).getScene();

        Stage dialogStage = new Stage();
        dialogStage.setTitle(view.getDisplayName());
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
            throw new RuntimeException("Could not load the object hierarchy of: " + viewFile);
            // TODO: Give error feedback
        }
    }

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
