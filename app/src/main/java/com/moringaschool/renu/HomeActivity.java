package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

//    View Binding
    @BindView(R.id.tvOrderUsername) TextView mOrderUsername;

    @BindView(R.id.gvMeals) GridView mGridView;

    @BindView(R.id.btnCheckout)
    Button mCheckout;

//    For logs
    private static final String TAG = "HomeActivity";

//    Data to be used
    private String[] foods = {"Fries", "Burger", "Pizza", "Pancakes", "Juice", "Soda", "Chapati", "Fish", "Rice", "Tea"};
    private String[] prices = {"200", "350", "700", "150", "200", "100", "50", "30", "70", "120"};
    private int[] images = {R.drawable.fries, R.drawable.burger, R.drawable.pizza, R.drawable.pancakes, R.drawable.juice, R.drawable.soda, R.drawable.chapati, R.drawable.fish, R.drawable.rice, R.drawable.tea};

//    Arraylist
    List<GridViewClass> allMealInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/HelloDarling.ttf");

        Intent intent = getIntent();
        mOrderUsername.setText("Welcome " + intent.getStringExtra("username"));

        for (int i = 0; i < foods.length; i += 1){

            GridViewClass gridViewClass = new GridViewClass(foods[i], prices[i], images[i]);
            allMealInfo.add(gridViewClass);
        }

        HomeMenuAdapter homeMenuAdapter = new HomeMenuAdapter(this, allMealInfo, typeface);
        mGridView.setAdapter(homeMenuAdapter);

        mCheckout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            if (v == mCheckout){
//                startActivity(new Intent(HomeActivity.this, CheckoutActivity.class));

            }
    }
}
