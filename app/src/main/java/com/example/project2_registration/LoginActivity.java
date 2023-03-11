package com.example.project2_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.project2_registration.Const.*;
import static com.example.project2_registration.Utils.showErrorDialog;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button registerButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupTexts();
        setupButtons();
    }

    private void setupTexts() {
        usernameEditText = findViewById(R.id.edtxt_username);
        passwordEditText = findViewById(R.id.edtxt_password);
    }

    private void setupButtons() {
        registerButton = findViewById(R.id.btn_register);
        loginButton = findViewById(R.id.btn_login);

        registerButton.setOnClickListener(this::onRegisterClick);
        loginButton.setOnClickListener(this::onLoginClick);
    }

    private void onRegisterClick(View v) {
        Intent registrationActivityIntent = new Intent(this, RegistrationActivity.class);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Save whatever data the user already inputted.
        if (!username.isEmpty()) {
            // Check if the user is already registered. If so, just login.
            SharedPreferences prefs = getApplication().getSharedPreferences(username, MODE_PRIVATE);
            if (prefs.contains(PREF_ALREADY_EXISTS)) {
                onLoginClick(loginButton);
                return;
            }

            registrationActivityIntent.putExtra(PREF_USERNAME, username);
        }
        if (!password.isEmpty()) {
            registrationActivityIntent.putExtra(PREF_PASSWORD, password);
        }

        startActivity(registrationActivityIntent);
        finish();
    }

    private void onLoginClick(View v) {
        String username = usernameEditText.getText().toString();

        SharedPreferences prefs = getApplication().getSharedPreferences(username, MODE_PRIVATE);

        // If that user doesn't exist yet, delete the prefs we just created
        // and fall back to a registration.
        if (!prefs.contains(PREF_ALREADY_EXISTS)) {
            getApplication().deleteSharedPreferences(username);
            onRegisterClick(registerButton);
            return;
        }

        // Validate password.
        String userPassword = passwordEditText.getText().toString();
        String actualPassword = prefs.getString(PREF_PASSWORD, "");
        if (!userPassword.equals(actualPassword)) {
            showErrorDialog(this, "Incorrect password.");
            return;
        }

        // Send to the main activity.
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        // Send the username
        mainActivityIntent.putExtra(PREF_USERNAME, username);
        startActivity(mainActivityIntent);
        finish();
    }
}