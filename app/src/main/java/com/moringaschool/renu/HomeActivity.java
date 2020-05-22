package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

//    View Binding
    @BindView(R.id.tvOrderUsername) TextView mOrderUsername;

    @BindView(R.id.gvMeals) GridView mGridView;

//    For logs
    private static final String TAG = "HomeActivity";

//    Data to be used
    private String[] foods = {"Fries", "Burger", "Pizza", "Pancakes", "Juice", "Soda", "Chapati", "Ndengu", "Rice", "Matumbo"};
    private int[] prices = {200, 350, 700, 150, 200, 100, 50, 30, 70, 120};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mOrderUsername.setText("Welcome " + intent.getStringExtra("username"));

        HomeMenuAdapter homeMenuAdapter = new HomeMenuAdapter(HomeActivity.this, foods, prices);
        mGridView.setAdapter(homeMenuAdapter);

        mGridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Your ordered: " + foods[position], Toast.LENGTH_SHORT).show();
    }
}
