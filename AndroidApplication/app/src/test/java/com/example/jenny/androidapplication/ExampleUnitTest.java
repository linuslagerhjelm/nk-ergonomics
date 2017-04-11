package com.example.jenny.androidapplication;

import com.example.jenny.androidapplication.Model.Office;
import com.example.jenny.androidapplication.Model.Score;
import com.example.jenny.androidapplication.Model.ServerConnection;
import com.example.jenny.androidapplication.Model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void TestGetUserByID() throws Exception {
        String id="1";
        ServerConnection server = new ServerConnection(id);
        JSONArray jobject=server.getUser(id);
        assertNotNull(jobject);
    }
    @Test
    public void TestGetUserByID1() throws Exception{
        String id="1";
        int id2=1;
        ServerConnection server = new ServerConnection(id);
        JSONArray jobject=server.getUser(id);
        JSONObject j = jobject.getJSONObject(0);
        int actualID=j.getInt("id");
        assertEquals(id2,actualID);
    }

    @Test
    public void TestSendDataToServer()throws Exception{
        String id="1";
        ServerConnection server = new ServerConnection(id);
        User user = new User(1,"Alexander","Lagerhjelm", Office.SKELLEFTEÅ);
        int value = 500;
        long timestamp = System.currentTimeMillis();
        Score score= new Score(value, timestamp, user);
        List<Score> highScore = new ArrayList<>();
        highScore.add(score);
        server.postScores(highScore);
    }

}