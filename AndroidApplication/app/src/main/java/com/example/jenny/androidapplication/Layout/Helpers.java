package com.example.jenny.androidapplication.Layout;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jenny.androidapplication.R;

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
        fromActivity.startActivity(intent);
    }
}
