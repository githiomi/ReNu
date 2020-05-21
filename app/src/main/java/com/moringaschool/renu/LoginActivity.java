package com.moringaschool.renu;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.PasswordAuthentication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static int mCounter = 5;

    @BindView(R.id.btnLogin) Button mLogin;

    @BindView(R.id.userUsername) TextView mUsername;

    @BindView(R.id.userPassword) PasswordAuthentication mPassword;

    @BindView(R.id.timesRemaining) TextView mTimesRemaining;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    public void validate(String theUsername, String thePassword) {

        String username = "Admin";
        String password = "12345";

        if (theUsername.equals(username) && thePassword.equals(password)) {

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("Username", theUsername);
            startActivity(intent);

        }else {
            mCounter -= 1;

            mTimesRemaining.setText(mCounter + "More Tries");

            if (mCounter == 0){
                mTimesRemaining.setText("No More Tries");
                mLogin.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v){
        if (v == mLogin){
            String enteredUsername = mUsername.getText().toString();
            String enteredPassword = mPassword.getPassword().toString();

            validate(enteredUsername, enteredPassword);
        }
    }
}
