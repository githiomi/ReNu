package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {

    @BindView(R.id.tvOrders)
    TextView mOrders;

    GridViewClass gridViewClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ButterKnife.bind(this);

        Intent getOrders = getIntent();

        if (getOrders.getExtras() != null){

            gridViewClass = (GridViewClass) getOrders.getSerializableExtra("item");

            mOrders.append( gridViewClass.getFoodName() + " that costs Ksh." + gridViewClass.getFoodPrice());

        }

    }
}
