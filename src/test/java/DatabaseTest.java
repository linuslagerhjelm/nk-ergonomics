import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Linus Lagerhjelm
 * File: DatabaseTest
 * Created: 2017-03-11
 * Description: Test cases for the Database class
 */
class DatabaseTest {

    private static final String DB_NAME = "testdb.db";
    private static Database db;
    private final User user = new User(1337, "Linus", "Lagerhjelm", User.Office.STOCKHOLM);

    @BeforeAll
    public static void setUp() throws Exception {
        db = new Database(DB_NAME);
        db.initialize();
    }

    @Test
    public void shouldInsertUser() {
        db.insertUser(user);
    }

    @Test
    public void shouldReadSingleUser() {
        User u1 = db.getUserByFullName("Linus", "Lagerhjelm");
        User u2 = new User(u1.getId(), "Linus", "Lagerhjelm", User.Office.STOCKHOLM);
        assertEquals(u2, u1);
    }

    @Test
    public void shouldReadUsersByOffice() {
        List<User> users = db.getUsersByOffice(User.Office.STOCKHOLM);
        assertNotEquals(0, users.size());
    }
}