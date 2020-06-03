package com.moringaschool.renu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.moringaschool.renu.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

//    Class References
    private static final String TAG = MainActivity.class.getSimpleName();
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(activity);

//        Runs the progressbar for a while before moving to the next intent
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent = new Intent(MainActivity.this, UserLogin.class);
               startActivity(intent);
            }
        }, 2500);
    }

}
