package com.example.project2_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.project2_registration.Const.*;

public class MainActivity extends AppCompatActivity {

    TextView usernameText;
    TextView firstNameText;
    TextView lastNameText;
    TextView emailText;
    TextView phoneText;
    TextView passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTexts();
    }

    private void setupTexts() {
        usernameText = findViewById(R.id.edtxt_username_main);
        firstNameText = findViewById(R.id.edtxt_firstname_main);
        lastNameText = findViewById(R.id.edtxt_lastname_main);
        emailText = findViewById(R.id.edtxt_email_main);
        phoneText = findViewById(R.id.edtxt_phone_main);
        passwordText = findViewById(R.id.edtxt_password_main);

        // Inject the user info from the prefs.
        String username = getIntent().getStringExtra(PREF_USERNAME);
        SharedPreferences prefs = getApplication().getSharedPreferences(username, MODE_PRIVATE);
        Resources res = getResources();
        usernameText.setText(res.getString(R.string.title_username, username));
        firstNameText.setText(res.getString(R.string.title_firstname, prefs.getString(PREF_FIRSTNAME, "")));
        lastNameText.setText(res.getString(R.string.title_lastname, prefs.getString(PREF_LASTNAME, "")));
        emailText.setText(res.getString(R.string.title_email, prefs.getString(PREF_EMAIL, "")));
        phoneText.setText(res.getString(R.string.title_phone, prefs.getString(PREF_PHONE, "")));
        passwordText.setText(res.getString(R.string.title_password, prefs.getString(PREF_PASSWORD, "")));
    }
}