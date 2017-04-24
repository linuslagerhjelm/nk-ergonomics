package com.example.jenny.androidapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jenny.androidapplication.Layout.Helpers;
import com.example.jenny.androidapplication.Layout.UserActivity;

public class MainActivity extends AppCompatActivity {

    private Button okButton;
    private Helpers helper = new Helpers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        addKeyListener();
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
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.user:
                // redirect to UserActivity
                helper.changeActivity(this,UserActivity.class);
                return true;
            case R.id.office:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void addKeyListener() {
        okButton = (Button) findViewById(R.id.button3);
        okButton.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Toast.makeText(MainActivity.this, okButton.getText(), Toast.LENGTH_LONG).show();
                    return true;

                } else if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_9)) {

                    Toast.makeText(MainActivity.this, "Number nine is pressed!", Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });
    }
}
