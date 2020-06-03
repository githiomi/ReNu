package com.moringaschool.renu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.moringaschool.renu.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSignUp extends AppCompatActivity implements View.OnClickListener{

//    TAG
    private static final String TAG = UserSignUp.class.getSimpleName();

//    Local variables
    private String userName;

//    Binding views
    @BindView(R.id.createUserButton) Button mCreateUserButton;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.loginTextView) TextView mLoginTextView;

//    Firebase Authentications
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListerner;

//    Progress Dialog variable
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

//        ButterKnife binding
        ButterKnife.bind(this);

//        Instantiating firebase authentication
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAuthListerner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if ( firebaseUser != null ){
                    Intent startTableActivity = new Intent(UserSignUp.this, HomeActivity.class);
                    startTableActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startTableActivity);
                    finish();
                }
            }
        };

//        Method that calls the progress dialog
        progressDialog();

//        To change the content of the app bar
        getSupportActionBar().setTitle("Re ~ Nu Sign Up");

//        Adding on click listeners to views
        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);

//        Setting on focus change listeners
        mNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
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
        mConfirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
    }

//    On click listener method
    @Override
    public void onClick(View view){
        if ( view == mLoginTextView ){
            Intent backToLogin = new Intent(UserSignUp.this, UserLogin.class);
            backToLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(backToLogin);
            finish();
        }

        if ( view == mCreateUserButton ){
            signUp();
        }
    }

//    Custom sign up method to authenticate a new account
    public void signUp() {

        final String name = mNameEditText.getText().toString().trim();
        userName = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        final String password = mPasswordEditText.getText().toString().trim();
        final String confirmedPassword = mConfirmPasswordEditText.getText().toString().trim();

        boolean isValidName = isValidName(name);
        boolean isValidUsername = isValidName(userName);
        boolean isValidEmail = isValidEmail(email);
        boolean isValidPassword = isValidPassword(password, confirmedPassword);

        if ( ! (isValidName) || ! (isValidUsername) || ! (isValidEmail) || ! (isValidPassword)) return;

//        Show the progress dialog after validation
        mProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mProgressDialog.dismiss();

                if ( task.isSuccessful() ){
                    String successful = "Sign up successful";
                    Toast.makeText(UserSignUp.this, successful, Toast.LENGTH_SHORT).show();

                    FirebaseUser firebaseUser = task.getResult().getUser();
                    createFirebaseUser(firebaseUser);
                }
                else {
                    String unsuccessful = "Sign up failed";
                    Toast.makeText(UserSignUp.this, unsuccessful, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    Custom method to hide keyboard on focus change
    public void hideKeyboard(View v){
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

//    Methods to validate user input for sign up
    private boolean isValidName(String name) {
        if (name.equals("")) {
            mNameEditText.setError("Please enter your name");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }

//    Custom method for the progress dialog
    public void progressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading ...");
        mProgressDialog.setMessage("Please hold as we sign you up");
        mProgressDialog.setCancelable(false);
    }

//    To add the username field to the firebase app
    private void createFirebaseUser(final FirebaseUser user) {

        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, user.getDisplayName());
                        }
                    }
                });
    }

//    Class over ride methods
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mFirebaseAuthListerner);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mFirebaseAuthListerner);
    }

}