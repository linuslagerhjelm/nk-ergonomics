package com.example.jenny.androidapplication.Layout;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jenny.androidapplication.Model.ServerConnection;
import com.example.jenny.androidapplication.Model.User;
import com.example.jenny.androidapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jenny on 2017-04-24.
 */

public class Helpers {

    /**
     * Change between the application views between from and toActivity
     * @param fromActivity
     * @param toActivity
     */
    public void changeActivity(AppCompatActivity fromActivity, Class<?> toActivity) {
        Intent intent = new Intent(fromActivity,toActivity);
        /**if (toActivity.equals(UserActivity.class)) {
            intent.putExtra();
        }**/
        fromActivity.startActivity(intent);
    }

    public JSONObject getHighscores(String url) {
        try {
            URL newContent = new URL(url);
            URLConnection mURLConnection = newContent.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(mURLConnection.getInputStream(), "utf-8"));
            StringBuilder out = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            JSONArray jArray = new JSONArray(out.toString());
            JSONObject jObject = jArray.getJSONObject(0);
            reader.close();
            return jObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**public void getHighscores(String url, ServerConnection.getHighscoreCallback callback) {
        new Thread(() -> {
            try {
                URL newContent = new URL(url);
                URLConnection mURLConnection = newContent.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(mURLConnection.getInputStream(), "utf-8"));
                StringBuilder out = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                JSONArray jArray = new JSONArray(out.toString());
                reader.close();
                callback.returnHighscores(jArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }**/
}
