package com.moringaschool.renu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringaschool.renu.MainActivity;
import com.moringaschool.renu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {

//    TAG
    private static final String TAG = UserLogin.class.getSimpleName();

//    Binding Views
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.passwordLoginButton) Button mLoginButton;
    @BindView(R.id.registerTextView) TextView mRegisterIfNoAccount;

//    Firebase Authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthStateListener;

//    Progress dialog
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

//        Butterknife binding
        ButterKnife.bind(this);

//        Function to change the app bar
        getSupportActionBar().setTitle("Re ~ Nu User Login");

//        Method to call the createProgressDialog
        createProgressDialog();

//        Setting methods to hide keyboard
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });

        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });

//        Setting on click listener for the login button
        mLoginButton.setOnClickListener(this);
        mRegisterIfNoAccount.setOnClickListener(this);

//       Initializing firebase authenticators
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if ( firebaseUser != null){
                    Intent loginIntent = new Intent(UserLogin.this, MainActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(loginIntent);
                    finish();
                }

            }
        };
    }

//    Method to hide keyboard
    public void hideKeyboard(View v){
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

//    On Click listener methods
    @Override
    public void onClick(View view){
        if ( view == mLoginButton ){
            login();
        }

        if ( view == mRegisterIfNoAccount){
            Intent signUpIntent = new Intent(UserLogin.this, UserSignUp.class);
            startActivity(signUpIntent);
            finish();
        }
    }

//    Custom method that allows user to login
    public  void login() {
        String loginEmail = mEmailEditText.getText().toString().trim();
        String loginPassword = mPasswordEditText.getText().toString().trim();

        if ( loginEmail.equals("") ){
            mEmailEditText.setError("Cannot be left blank");
            return;
        }

        if ( loginPassword.equals("") ){
            mPasswordEditText.setError("Cannot be left blank");
            return;
        }

//        Display after validation input
        mProgressDialog.show();

        mAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mProgressDialog.dismiss();

                if (task.isSuccessful()) {
                    String authentication = "Authentication Successful";
                    Toast.makeText(UserLogin.this, authentication, Toast.LENGTH_SHORT).show();
                }
                String noAuthentication = "Incorrect username or password";
                Toast.makeText(UserLogin.this, noAuthentication, Toast.LENGTH_LONG).show();
            }
        });
    }

//    custom method for the progress dialog
    public void createProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please hold...");
        mProgressDialog.setMessage("Logging you into your account");
        mProgressDialog.setCancelable(false);
    }

//    Class overrides
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mFirebaseAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mFirebaseAuthStateListener);
    }

}