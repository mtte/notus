package kaymattern.notus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Map<View, Scene> sceneCache = new HashMap<>();

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
            this.stage.setScene(getScene(view));
            this.stage.setTitle(view.getDisplayName());
        }
    }

    /**
     * Shows the given view in a dialog.
     * @param view The view to show in the dialog
     */
    private void showDialog(View view) {
        Scene scene = getScene(view);

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
    private Scene getScene(View view) {
        if (! sceneCache.containsKey(view)) {
            sceneCache.put(view, loadScene(view));
        }
        return sceneCache.get(view);
    }

    /**
     * Load the scene of the corresponding view.
     * @param view The view
     * @return The loaded scene
     */
    private Scene loadScene(View view) {
        Optional<Scene> scene = Optional.empty();
        String viewFile = String.format("kaymattern/views/%s%s.fxml",
                view.isDialog() ? "dialogs/" : "",
                view.getName());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(viewFile));
            Parent parent = loader.load();
            NotusController controller = loader.getController();
            controller.setUp(this.app);
            scene = Optional.of(new Scene(parent));
        } catch (IOException e) {
            if (this.stage.getScene() == null) {
                throw new RuntimeException("Could not load the object hierarchy of: " + viewFile);
            }
            // TODO: Give error feedback
        }
        return scene.orElse(this.stage.getScene());
    }

}
