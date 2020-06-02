package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moringaschool.renu.ui.MealViewActivity;


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

        mOrderUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });

        if (name != null) {
            mOrderUsername.setText("Welcome back " + name + "!");
        }else{
            mOrderUsername.setText("Take another order!");
        }
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

    public void hideKeyboard(View v){
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
