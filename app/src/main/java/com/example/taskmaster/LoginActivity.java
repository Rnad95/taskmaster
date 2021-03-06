package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String USERNAME = "username";
    private ProgressBar loadingProgressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         EditText usernameEditText = findViewById(R.id.email);
         EditText passwordEditText = findViewById(R.id.password);
         Button loginButton = findViewById(R.id.login);
         TextView signUpPrompt = findViewById(R.id.sign_up_prompt);
        String username =usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        loadingProgressBar = findViewById(R.id.loading);

        signUpPrompt.setOnClickListener(view -> {
            Intent navigateToSignUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(navigateToSignUpIntent);
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    usernameEditText.getText().toString();
//                    passwordEditText.getText().toString();

                    loginButton.setEnabled(true);

                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(String str) {
        String welcome = getString(R.string.welcome) +"";
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void login(String username, String password) {
        Amplify.Auth.signIn(
                username,
                password,
                result -> {
                    Log.i(TAG, result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                    loadingProgressBar.setVisibility(View.INVISIBLE);

                    startActivity( new Intent(LoginActivity.this, MainActivity.class));
                },
                error -> Log.e(TAG, error.toString())
        );
    }
}
