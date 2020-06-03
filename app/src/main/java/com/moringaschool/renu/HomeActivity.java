package com.moringaschool.renu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

//    Firebase authentication variable to get username
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Butter Knife binding
        ButterKnife.bind(this);

//        Custom method meant to hide the keyboard
        mOrderUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });

//        Landing page header
//        Typeface typeface = new Typeface();

        mOrderUsername.setText("Enter the table number you are waiting on!");
//        mOrderUsername.setTypeface(typeface);

//        Firebase methods to obtain the users username
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String username = firebaseUser.getDisplayName();

                if ( username != null ){
                    getSupportActionBar().setTitle(username);
                }
                else{
                    getSupportActionBar().setTitle("Welcome");
                }
            }
        };

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

//    Class over ride methods
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

}
