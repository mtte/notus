package kaymattern.notus;

import javafx.scene.control.Alert;
import kaymattern.notus.database.DataAccessor;

/**
 * Notus Application Interface.
 */
public interface App {

    /**
     * Show the view on the main stage.
     * @param view The view to show
     */
    void showView(View view);

    /**
     * Show a dialog on the main stage.
     * @param alertType The type of the alert
     * @param title The title of the alert
     * @param headerText The header text of the alert
     * @param contentText The content of the alert
     * @return If the user clicked OK
     */
    boolean showAlert(Alert.AlertType alertType, String title, String headerText, String contentText);

    /**
     * Retrieve the controller of a view.
     * @param view The view of which to get the controller.
     * @return The controller
     */
    NotusController getControllerOfView(View view);

    /**
     * Returns the data accessor of Notus.
     * @return The data accessor
     */
    DataAccessor getDataAccessor();

}
