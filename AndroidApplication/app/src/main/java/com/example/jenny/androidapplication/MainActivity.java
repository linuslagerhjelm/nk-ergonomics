package com.example.jenny.androidapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        if(infoButton!=null){
            infoButton.setOnClickListener(v -> InformationBox(message));
        }
        // Controls if the user presses the okbutton and enters the code
        button3 = (Button)findViewById(R.id.button3);
        if(button3!=null){
            button3.setOnClickListener(v -> {
                EditText editText = (EditText) findViewById(R.id.editText);
                if(editText!=null){
                    String value = editText.getText().toString();
                    ServerConnection server = new ServerConnection(value, "http://10.0.2.2:4567/api/getUsers?id=", user -> {
                        helper.changeActivity(MainActivity.this,StartActivity.class);
                    });
                }
            });
        }


    }

    public void InformationBox(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
