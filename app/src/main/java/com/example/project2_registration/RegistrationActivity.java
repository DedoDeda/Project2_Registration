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

public class RegistrationActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText phoneEditText;
    EditText passwordEditText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupTexts();
        setupButtons();
    }

    private void setupTexts() {
        usernameEditText = findViewById(R.id.edtxt_username_reg);
        firstNameEditText = findViewById(R.id.edtxt_firstname);
        lastNameEditText = findViewById(R.id.edtxt_lastname);
        emailEditText = findViewById(R.id.edtxt_email);
        phoneEditText = findViewById(R.id.edtxt_phone);
        passwordEditText = findViewById(R.id.edtxt_password_reg);

        // Try to retrieve and set username and password from the previous activity.
        Intent intent = getIntent();
        if (intent.hasExtra(PREF_USERNAME) && intent.hasExtra(PREF_PASSWORD)) {
            String username = intent.getStringExtra(PREF_USERNAME);
            String password = intent.getStringExtra(PREF_PASSWORD);

            usernameEditText.setText(username);
            passwordEditText.setText(password);
        }
    }

    private void setupButtons() {
        submitButton = findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(this::onSubmitClick);
    }

    private void onSubmitClick(View v) {
        String username = usernameEditText.getText().toString();
        if (username.isEmpty()) {
            showErrorDialog(this, "Username must be filled.");
            return;
        }

        String firstName = firstNameEditText.getText().toString();
        if (firstName.isEmpty()) {
            showErrorDialog(this, "First name must be filled.");
            return;
        }

        String lastName = lastNameEditText.getText().toString();
        if (lastName.isEmpty()) {
            showErrorDialog(this, "Last name must be filled.");
            return;
        }

        String email = emailEditText.getText().toString();
        if (email.isEmpty()) {
            showErrorDialog(this, "Email must be filled.");
            return;
        }

        String phone = phoneEditText.getText().toString();
        if (phone.isEmpty()) {
            showErrorDialog(this, "Phone must be filled.");
            return;
        }

        String password = passwordEditText.getText().toString();
        if (password.length() < 4) {
            showErrorDialog(this, "Password must contain at least 4 characters.");
            return;
        }

        // Save to to shared prefs.
        SharedPreferences prefs = getApplication().getSharedPreferences(username, MODE_PRIVATE);
        SharedPreferences.Editor prefsEd = prefs.edit();

        // Mark these prefs as 'already exist'.
        prefsEd.putBoolean(PREF_ALREADY_EXISTS, true);

        prefsEd.putString(PREF_FIRSTNAME, firstName);
        prefsEd.putString(PREF_LASTNAME, lastName);
        prefsEd.putString(PREF_EMAIL, email);
        prefsEd.putString(PREF_PHONE, phone);
        prefsEd.putString(PREF_PASSWORD, password);

        prefsEd.commit();

        // Send to the main activity.
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        // Send the username.
        mainActivityIntent.putExtra(PREF_USERNAME, username);
        startActivity(mainActivityIntent);
        finish();
    }
}