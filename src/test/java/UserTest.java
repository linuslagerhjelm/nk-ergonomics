import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Linus Lagerhjelm
 * File: UserTest
 * Created: 2017-03-12
 * Description: Test cases for the User class
 */
class UserTest {
    private User u1 = new User(1337, "Linus", "Lagerhjelm", User.Office.STOCKHOLM);
    private User u2 = new User(10, "Viktor", "Åhlund", User.Office.SKELLEFTEÅ);

    @Test
    public void shouldBeEqual() {
        assertEquals(u1, u1);
    }

    @Test
    public void shouldNotBeEqual() {
        assertNotEquals(u1, u2);
    }

    @Test
    public void shouldHaveSameHashCode() {
        assertEquals(u1.hashCode(), u1.hashCode());
    }

    @Test
    public void shouldNotHaveSameHashCode() {
        assertNotEquals(u1.hashCode(), u2.hashCode());
    }

}