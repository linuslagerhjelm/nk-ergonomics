import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: Database
 * Created: 2017-03-11
 * Description: Provides an API for saving and reading data from a database
 */
class Database {
    interface PrepareCallback {
        PreparedStatement prepare(Connection connection) throws SQLException;
    }

    private final String mDB_NAME;

    Database(String filename) {
        mDB_NAME = "jdbc:sqlite:" + filename;
    }

    /**
     * Verifies that the current database has the expected structure.
     * Creates all the missing tables.
     */
    void initialize() {
        final String query = "CREATE TABLE IF NOT EXISTS users (" +
                "    id        INTEGER PRIMARY KEY AUTOINCREMENT" +
                "                       NOT NULL," +
                "    first_name TEXT    NOT NULL," +
                "    last_name  TEXT    NOT NULL," +
                "    office     STRING  DEFAULT SKELLEFTEÅ" +
                "                       NOT NULL" +
                ");";

        executeUpdate((connection -> connection.prepareStatement(query) ));
    }

    /**
     * Creates a new database entry for the provided user
     * @param user the user to add
     */
    void insertUser(User user) {
        final String query = "INSERT INTO users (first_name, last_name, office) VALUES (?, ?, ?)";
        executeUpdate((connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getOffice().name());
            return statement;
        }));
    }

    /**
     * Retrieves a single user from the database using the full name
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @return the matched user
     */
    User getUserByFullName(String firstName, String lastName) {
        final String query = "SELECT * FROM users u WHERE u.first_name = ? AND u.last_name = ?;";
        List<User> users = executeSelect(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            return statement;
        });

        return users.get(0);
    }

    /**
     * Gets all the users from a specific office
     * @param office the office to match users from
     * @return list of matched uses empty if no users matched
     */
    List<User> getUsersByOffice(User.Office office) {
        final String query = "SELECT * FROM users u WHERE u.office = ?;";
        return executeSelect(connection -> {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, office.name());
            return stmt;
        });
    }

    /**
     * Handles the necessary operations to execute a prepared update
     * @param callback callback that prepares and executes a query
     */
    private void executeUpdate(PrepareCallback callback) {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(mDB_NAME);
            PreparedStatement statement = callback.prepare(connection);
            statement.setQueryTimeout(10);  // NOTE: Value is given in seconds
            statement.execute();
            statement.close();

        } catch(SQLException | ClassNotFoundException e) {
            // We do not want to propagate errors outside this class
            e.printStackTrace();

        } finally {
            try {
                if(connection != null)
                    connection.close();

            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the necessary operations to execute a select query on the db
     * @param callback callback that prepares and executes a query
     * @return ResultSet containing the result of the query. Can be null if
     * if an exception were thrown or if there were no result set from db.
     */
    private List<User> executeSelect(PrepareCallback callback) {
        List<User> users = null;
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(mDB_NAME);
            PreparedStatement statement = callback.prepare(connection);
            statement.setQueryTimeout(10);  // NOTE: Value is given in seconds
            users = parseUsers(statement.executeQuery());

        } catch(SQLException | ClassNotFoundException e) {
            // We do not want to propagate errors outside this class
            e.printStackTrace();

        } finally {
            try {
                if(connection != null)
                    connection.close();

            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    /**
     * Handles the parsing of users from a result set into a list of users
     * @param resultSet the result set to extract users from
     * @return list of users
     */
    private List<User> parseUsers(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        try {
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String first = resultSet.getString("first_name");
                String last = resultSet.getString("last_name");
                User.Office office = User.Office.valueOf(resultSet.getString("office"));
                users.add(new User(id, first, last, office));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
