package kaymattern.notus.views;

import kaymattern.notus.App;
import kaymattern.notus.NotusController;

public abstract class AbstractNotusController implements NotusController {

    private App app;

    @Override
    public final void setUp(App app) {
        this.app = app;
        init();
    }

    /**
     * Initializes the controller.
     */
    protected abstract void init();

    /**
     * Get the app
     * @return The app
     */
    protected App getApp() {
        return this.app;
    }

}
