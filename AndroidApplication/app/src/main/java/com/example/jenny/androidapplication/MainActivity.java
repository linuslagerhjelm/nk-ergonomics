package com.example.jenny.androidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jenny.androidapplication.Layout.Helpers;
import com.example.jenny.androidapplication.Layout.StartActivity;
import com.example.jenny.androidapplication.Layout.UserActivity;
import com.example.jenny.androidapplication.Model.ServerConnection;

public class MainActivity extends AppCompatActivity {

    Button button3;
    private Helpers helper = new Helpers();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /**EditText editText = (EditText) findViewById(R.id.editText);
                String value = editText.getText().toString();
                ServerConnection server = new ServerConnection(value);
                boolean doesUserExcist=server.controlIfUserExcist();
                if(doesUserExcist){**/
                    helper.changeActivity(MainActivity.this,StartActivity.class);
                /**}
                else{
                }**/
            }
        });

    }
}
