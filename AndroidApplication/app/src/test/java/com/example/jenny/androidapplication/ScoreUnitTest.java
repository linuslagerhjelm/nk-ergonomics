package com.example.jenny.androidapplication;
import com.example.jenny.androidapplication.Model.Office;
import com.example.jenny.androidapplication.Model.Score;
import com.example.jenny.androidapplication.Model.User;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jenny on 2017-04-19.
 */

public class ScoreUnitTest {
    /**First Score**/
    User user = new User(1,"Alexander","Lagerhjelm", Office.SKELLEFTEÅ);
    int value = 500;
    long timestamp = System.currentTimeMillis();
    Score score= new Score(value, timestamp, user);
    /**Second Score**/
    User user2 = new User(1,"Johannes","Trevlig", Office.LA);
    int value2 = 20;
    long timestamp2 = System.currentTimeMillis();
    Score newScore= new Score(value2, timestamp2, user2);
    /**Test if a score is created**/
    @Test
    public void TestScore(){
        assertNotNull(score);
    }

    @Test
    public void TestScoreIsValid(){
        boolean valid = score.valid();
        assertTrue(valid);
    }
    @Test
    public void shouldNotBeEqual() {
        assertNotEquals(score, newScore);
    }

    @Test
    public void shouldHaveSameHashCode() {
        assertEquals(score.hashCode(), score.hashCode());
    }

    @Test
    public void shouldNotHaveSameHashCode() {
        assertNotEquals(score.hashCode(), newScore.hashCode());
    }

}
