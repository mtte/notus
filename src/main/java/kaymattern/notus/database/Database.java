package kaymattern.notus.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class to manage a SQLite database
 */
public class Database {

    private final String name;

    /**
     * Constructor.
     * @param name The name of the database (basically the name of the db file)
     */
    protected Database(String name) {
        this.name = name;
        try {
            initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException("Fatal Error: Could not initialize the database", e);
        }
    }

    /**
     * Gets a connection to this database.
     * @return A connection to the database
     * @throws SQLException If a database error occured
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + name + ".sqlite");
    }

    /**
     * Executes the given query.
     * @param query The query to execute
     * @return The result of the query converted to a two dimensional object array
     */
    protected Object[][] executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            Object[][] converted = convertResultSet(result);
            result.close();
            return converted;
        } catch (SQLException e) {
            System.err.println("Error: Execution of query failed: " + query);
            e.printStackTrace();
        }

        return new Object[0][0];
    }

    /**
     * Executes the given SQL.
     * @param sql The SQL statement to execute
     * @return Row count, zero if no return or minus one if an error occurred
     */
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

    /**
     * Executes the given query as a prepared statement.
     * @param sql The query to execute
     * @param args The arguments for the prepared statement
     * @return The result of the query converted to a two dimensional object array
     */
    protected Object[][] executePreparedStatement(String sql, Object... args) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (!preparedStatementIsValid(sql, args.length)) {
                throw new IllegalArgumentException("Invalid amount of arguments for '" + sql + "'. ");
            }
            fillPreparedStatement(preparedStatement, args);
            ResultSet result = preparedStatement.executeQuery();
            Object[][] converted = convertResultSet(result);
            result.close();
            return  converted;
        } catch (SQLException e) {
            System.err.println("Error: Execution of query failed: " + sql);
            e.printStackTrace();
        }
        return new Object[0][0];
    }

    /**
     * Executes the given SQL as prepared statement.
     * @param sql The SQL statement to execute
     * @param args The arguments for the prepared statement
     * @return Row count, zero if no return or minus one if an error occurred
     */
    protected int executePreparedUpdate(String sql, Object... args) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (!preparedStatementIsValid(sql, args.length)) {
                throw new IllegalArgumentException("Invalid amount of arguments for '" + sql + "'. ");
            }
            fillPreparedStatement(preparedStatement, args);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: Execution of query failed: " + sql);
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Validates the number of arguments for a prepared statement.
     * @param sql The prepared statement
     * @param numberOfArguments The number of arguments passed to the prepared statement
     * @return The validity of the number of arguments
     */
    private boolean preparedStatementIsValid(String sql, int numberOfArguments) {
        return numberOfArguments == sql.length() - sql.replace("?", "").length();
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Object... args) throws SQLException {
        for (int i = 1; i <= args.length; i++) {
            Object arg = args[i - 1];
            if (arg instanceof String) {
                preparedStatement.setString(i, (String) arg);
            } else if (arg instanceof Integer) {
                preparedStatement.setInt(i, (Integer) arg);
            } else if (arg instanceof LocalDate) {
                preparedStatement.setDate(i , Date.valueOf((LocalDate) arg));
            } else {
                throw new SQLException("Unhandled type found!");
            }
        }
    }

    private Object[][] convertResultSet(ResultSet result) throws SQLException {
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
        return data.toArray(new Object[data.size()][]);
    }

    /**
     * Intializes the database.
     * Creates al needed tables if they do not already exist.
     * @throws SQLException If the initialization fails
     */
    private void initializeDatabase() throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS subject (" +
                    "id INTEGER PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS mark (" +
                    "id INTEGER PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "date DATE," +
                    "value TINYINT NOT NULL," +
                    "weight FLOAT NOT NULL," +
                    "subject_id INT NOT NULL," +
                    "FOREIGN KEY (subject_id) REFERENCES subject(id));");

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
