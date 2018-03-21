package kaymattern.notus.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to manage a SQLite database
 */
public class Database {

    private String name;

    protected Database(String name) {
        this.name = name;
        try {
            initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException("Fatal Error: Could not initialize the database", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + name + ".sqlite");
    }

    protected Object[][] executeQuery(String query) {
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int columns = metaData.getColumnCount();
            ArrayList<Object[]> data = new ArrayList<>();
            while (result.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i -1] = result.getObject(i);
                }
                data.add(row);
            }
            result.close();
            return data.toArray(new Object[data.size()][]);
        } catch (SQLException e) {
            System.err.println("Error: Execution of query failed: " + query);
            e.printStackTrace();
        }

        return new Object[0][0];
    }

    protected int executeUpdate(String sql) {
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error: Execution of update failed: " + sql);
            e.printStackTrace();
        }
        return -1;
    }

    private void initializeDatabase() throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS subject (" +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL);");
            statement.executeUpdate("CREATE MARK IF NOT EXISTS mark (" +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "date DATE," +
                    "value TINYINT NOT NULL," +
                    "weight FLOAT NOT NULL);");

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

}
