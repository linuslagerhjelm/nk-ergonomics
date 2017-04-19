package com.example.jenny.androidapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addKeyListener();
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
