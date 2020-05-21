package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private String[] foods = {"Fries", "Burger", "Pizza", "Pancakes", "Juice", "Soda", "Chapati", "Ndengu", "Rice", "Matumbo"};
    private int[] prices = {200, 350, 700, 150, 200, 100, 50, 30, 70, 120};

    @BindView(R.id.tvOrderUsername)
    TextView mOrderUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String user = intent.getStringExtra("username");


        mOrderUsername.setText("Welcome " + user + " !");
    }
}
