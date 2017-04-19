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
    private static final User user = new User(1337, "Linus", "Lagerhjelm", User.Office.STOCKHOLM);

    @BeforeAll
    public static void setUp() throws Exception {
        db = Database.getInstance(DB_NAME);
        db.initialize();
        db.insertUser(user);
    }

    @Test
    public void shouldInsertUser() {
        db.insertUser(user);
    }

    @Test
    public void shouldReadSingleUser() throws Exception {
        User u1 = db.getUsersByFullName("Linus", "Lagerhjelm").get(0);
        User u2 = u1.getModifiedCopy("Linus", "Lagerhjelm", User.Office.STOCKHOLM);
        assertEquals(u2, u1);
    }

    @Test
    public void shouldThrowException() {
        try {
            db.getUsersByFullName("foo", "baar");
            fail("Expected NoSuchUserException");

        } catch (NoSuchUserException e) {
            // Test successful
        }
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        User u1 = db.getUsersByFullName("Linus", "Lagerhjelm").get(0);
        User u2 = u1.getModifiedCopy("Alexander", "Lagerhjelm", User.Office.STOCKHOLM);
        db.updateUser(u2);
        assertNotEquals(0, db.getUsersByFullName("Alexander", "Lagerhjelm"));
    }

    @Test
    public void shouldHandleUpdateOfNonExistingUser() {
        db.updateUser(user);
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        db.insertUser(user);
        List<User> originalUsers = db.getUsersByFullName("Linus", "Lagerhjelm");
        User u1 = originalUsers.get(0);
        db.deleteUser(u1);
        assertNotEquals(originalUsers.size(), db.getUsersByFullName("Linus", "Lagerhjelm").size());
    }

    @Test
    public void shouldReadUsersByOffice() {
        List<User> users = db.getUsersByOffice(User.Office.STOCKHOLM);
        assertNotEquals(0, users.size());
    }

    @Test
    public void shouldInsertHighScore() {
        Score score = new Score(10, System.currentTimeMillis(), user);
        db.insertScores(new Score[]{score});
    }
}