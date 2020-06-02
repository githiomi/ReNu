package com.moringaschool.renu.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.moringaschool.renu.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSignUp extends AppCompatActivity {

//    Binding views
    @BindView(R.id.registerTextView) TextView mOptionToRegisterTextView;
    @BindView(R.id.passwordLoginButton) Button mPasswordLoginButton;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

//        ButterKnife binding
        ButterKnife.bind(this);
    }
}