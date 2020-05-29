package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Activity activity = this;

    @BindView(R.id.btnProceed) Button mProceed;
    @BindView(R.id.tvAppName) TextView mAppName;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(activity);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProceed.setVisibility(View.VISIBLE);
                mAppName.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        }, 3000);

        mProceed.setOnClickListener(this);

        }

        @Override
        public void onClick(View v){
            if ( v == mProceed){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
}
