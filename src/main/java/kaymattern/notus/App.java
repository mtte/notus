package kaymattern.notus;

import kaymattern.notus.database.DataAccessor;

/**
 * Notus Application Interface.
 */
public interface App {

    /**
     * Show the veiw on the main stage.
     * @param view The view to show
     */
    void showView(View view);

    /**
     * Returns the data accessor of Notus.
     * @return The data accessor
     */
    DataAccessor getDataAccessor();

}
