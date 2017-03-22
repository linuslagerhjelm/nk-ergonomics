import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: Database
 * Created: 2017-03-11
 * Description: Provides an API for saving and reading data from a database.
 * Uses the singleton pattern in order to don't have multiple DB connections
 * laying around.
 */
class Database {
    interface PrepareCallback {
        PreparedStatement prepare(Connection connection) throws SQLException;
    }

    private final String mDB_NAME;
    private static Database mInstance;

    private Database(String filename) {
        mDB_NAME = "jdbc:sqlite:" + filename;
    }

    /**
     * Returns the active instance of the Database class or creates
     * a new one if there were no active instances. Uses default database
     * @return the active database instance
     */
    static synchronized Database getInstance() {
        if (mInstance == null) {
            mInstance = new Database("nk-ergonomics.db");
            mInstance.initialize();
        }
        return mInstance;
    }

    /**
     * Returns the active instance of the Database object.
     * This method is a convenience method that allows for moc-db:s to be
     * used while testing. Use {@link #getInstance()} to use the default.
     * @param filename the active database instance
     * @return active instance of the Database
     */
    static synchronized Database getInstance(String filename) {
        if (mInstance == null) {
            mInstance = new Database(filename);
            mInstance.initialize();
        }
        return mInstance;
    }

    /**
     * Verifies that the current database has the expected structure.
     * Creates all the missing tables.
     */
    void initialize() {
        final String query1 = "CREATE TABLE IF NOT EXISTS users (" +
                "    id        INTEGER PRIMARY KEY AUTOINCREMENT" +
                "                       NOT NULL," +
                "    first_name TEXT    NOT NULL," +
                "    last_name  TEXT    NOT NULL," +
                "    office     STRING  DEFAULT SKELLEFTEÅ" +
                "                       NOT NULL" +
                ");";

        final String query2 = " CREATE TABLE IF NOT EXISTS scores (" +
                "    id         INTEGER PRIMARY KEY NOT NULL," +
                "    value      INTEGER NOT NULL," +
                "    timestamp  INTEGER NOT NULL," +
                "    user_id    INTEGER" +
                ");";

        executeUpdate((connection -> connection.prepareStatement(query1) ));
        executeUpdate((connection -> connection.prepareStatement(query2) ));
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
    List<User> getUsersByFullName(String firstName, String lastName) throws NoSuchUserException {
        final String query = "SELECT * FROM users u WHERE u.first_name = ? AND u.last_name = ?;";
        List<User> users = executeSelect(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            return statement;
        });

        if (users.size() == 0) {
            throw new NoSuchUserException();
        }
        return users;
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
     * Updates a user in the database to reflect the changes present in
     * the provided user object. The id field in the object will be used
     * to identify the correct row in db and therefore should the provided
     * object contain the same id as the user that should be updated.
     * @param user the new values for the user
     */
    void updateUser(User user) {
        final String query = "" +
                "UPDATE users " +
                "SET first_name = ?, last_name = ?, office = ?" +
                "WHERE users.id = ?";
        executeUpdate(connection -> {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getOffice().name());
            stmt.setInt(4, user.getId());
            return stmt;
        });
    }

    /**
     * Deletes the provided user from the Database. Identification is used
     * by using the id field in the provided user object.
     * @param user the user to delete
     */
    void deleteUser(User user) {
        final String query = "DELETE FROM users WHERE id = ?";
        executeUpdate(connection -> {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, user.getId());
            return stmt;
        });
    }

    /**
     * Adds the provided scores to the database
     * @param scores list of scores to add
     */
    void insertScores(Score[] scores) {
        final String query = "INSERT INTO scores (value, timestamp, user_id) VALUES (?,?,?)";
        executeBatchUpdate(connection -> {
            PreparedStatement stmt = connection.prepareStatement(query);
            Arrays.stream(scores).forEach(score -> {
                try {
                    stmt.setInt(1, score.getValue());
                    stmt.setLong(2, score.getTimestamp());
                    stmt.setInt(3, score.getUser().getId());
                    stmt.addBatch();
                } catch (SQLException e) {}
            });
            return stmt;
        });
    }

    /**
     * Handles the necessary operations to execute a prepared update
     * @param callback callback that prepares a query
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
     * Executes a batch update
     * @param callback callback that prepares a query
     */
    private void executeBatchUpdate(PrepareCallback callback) {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(mDB_NAME);
            PreparedStatement statement = callback.prepare(connection);
            statement.setQueryTimeout(10);  // NOTE: Value is given in seconds
            statement.executeBatch();
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
     * @param callback callback that prepares a query
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
