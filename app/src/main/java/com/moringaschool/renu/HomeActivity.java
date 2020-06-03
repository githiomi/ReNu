package com.moringaschool.renu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringaschool.renu.ui.MealViewActivity;
import com.moringaschool.renu.ui.UserLogin;


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
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/CHICKEN Pie.ttf");

        mOrderUsername.setText("Enter the table number you are waiting on!");
        mOrderUsername.setTypeface(typeface);

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

//    Over ride methods to inflate the menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.threedot_dropdown, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if ( id == R.id.goToProfile ){
            Toast.makeText(this, "You cannot view your profile", Toast.LENGTH_SHORT).show();
            return true;
        }
        if ( id == R.id.goToSettings ){
            Toast.makeText(this, "You cannot view the settings", Toast.LENGTH_SHORT).show();
            return true;
        }
        if ( id == R.id.logoutOfAccount ){
            logout();
            return true;
        }
        return onOptionsItemSelected(menuItem);
    }

//    The firebase logout function
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent logoutBackToLogin = new Intent (HomeActivity.this, UserLogin.class);
        logoutBackToLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logoutBackToLogin);
        finish();
    }

//    Custom method to hide the keyboard
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
