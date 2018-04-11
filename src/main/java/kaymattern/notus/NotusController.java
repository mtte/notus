package kaymattern.notus;

/**
 * Interface for a view controller.
 */
public interface NotusController {

    /**
     * Call this method to set up the controller.
     * @param app The app
     */
    void setUp(App app);

    /**
     * Call this method when the view is entered / shown.
     * As default the it does nothing.
     */
    default void entered() {}

}
