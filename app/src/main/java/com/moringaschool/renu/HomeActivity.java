package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.moringaschool.renu.ui.MealViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

//    View Binding
    @BindView(R.id.tvOrderHeader) TextView mOrderUsername;
    @BindView(R.id.ptTableToWaitOn) EditText mTableToWaitOn;
    @BindView(R.id.btnOrder) Button mProceedToOrder;

//    For logs
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Intent receivedIntent = getIntent();
        String name = receivedIntent.getStringExtra("username");

        mOrderUsername.setText("Welcome back " + name + "!" );

        mProceedToOrder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            if (v == mProceedToOrder){
                Intent sentIntent = new Intent(HomeActivity.this, MealViewActivity.class);
                String tableNumber = mTableToWaitOn.getText().toString();
                Log.v(TAG, "Table number to wait on: " + tableNumber);
                sentIntent.putExtra("tableNumber", tableNumber);
                startActivity(sentIntent);

            }
    }
}
