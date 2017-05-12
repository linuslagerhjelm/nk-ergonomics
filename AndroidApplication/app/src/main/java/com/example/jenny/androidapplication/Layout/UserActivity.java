package com.example.jenny.androidapplication.Layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jenny.androidapplication.Model.GlobalStore;
import com.example.jenny.androidapplication.Model.ServerConnection;
import com.example.jenny.androidapplication.Model.User;
import com.example.jenny.androidapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {
    Helpers helper = new Helpers();
    GlobalStore gs = GlobalStore.getInstance();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        JSONArray jarray=new JSONArray();
        this.helper.getHighscores("http://localhost:4567/api/getHighScores?startTime=0",jsonArr->{
            jarray.put(jsonArr);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                helper.changeActivity(this,StartActivity.class);
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.user:
                // redirect to UserActivity
                helper.changeActivity(this,UserActivity.class);
                return true;
            case R.id.office:
                // redirect to OfficeActivity
                helper.changeActivity(this, OfficeActivity.class);

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
