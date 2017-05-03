package com.example.jenny.androidapplication;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jenny.androidapplication.Layout.Helpers;
import com.example.jenny.androidapplication.Layout.StartActivity;
import com.example.jenny.androidapplication.Model.ServerConnection;

public class MainActivity extends AppCompatActivity {

    Button button3;
    ImageButton infoButton;
    private Helpers helper = new Helpers();
    String message = "Enter code, the code is individual and can be found in the "
            + "chrome plugin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoButton = (ImageButton) findViewById(R.id.infoButton);
        infoButton.setOnClickListener(v -> {
            InformationBox(message);
        });
        // Controls if the user presses the okbutton and enters the code
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            EditText editText = (EditText) findViewById(R.id.editText);
            String value = editText.getText().toString();
            ServerConnection server = new ServerConnection(value, "http://10.0.2.2:4567/api/getUsers?id=", user -> {

            //boolean doesUserExcist=server.controlIfUserExcist();
            helper.changeActivity(MainActivity.this,StartActivity.class);
            //if(doesUserExcist){
                //helper.changeActivity(MainActivity.this,StartActivity.class);
            //}
            //else{
                //InformationBox(message);
            //}
            });
        });

    }

    public void InformationBox(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
