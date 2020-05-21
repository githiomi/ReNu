package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;

public class HomeActivity extends AppCompatActivity {

    private String[] foods = {"Fries", "Burger", "Pizza", "Pancakes", "Juice", "Soda", "Chapati", "Ndengu", "Rice", "Matumbo"};
    private int[] prices = {200, 350, 700, 150, 200, 100, 50, 30, 70, 120};

    @BindView(R.id.tvOrderUsername)
    TextView mOrderUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");


        mOrderUsername.setText("Welcome " + username + "!");
    }
}
