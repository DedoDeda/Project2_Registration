package com.example.project2_registration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static com.example.project2_registration.Const.*;
import static com.example.project2_registration.Utils.showErrorDialog;

public class RegistrationActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button submitButton;

    private ActivityResultLauncher<Void> pickProfileLauncher;
    /**
     * Did the user modify the profile picture?
     */
    private boolean bModifiedProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupProfileImage();
        setupTexts();
        setupButtons();
    }

    private void setupProfileImage() {
        profileImageView = findViewById(R.id.img_profile);
        profileImageView.setOnClickListener(this::onProfileImageClick);
        // Allow the user to clear by holding the image.
        profileImageView.setOnLongClickListener(this::onProfileImageLongClick);

        pickProfileLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicturePreview(),
                this::onPickProfileActivityResult);
    }

    private void onProfileImageClick(View v) {
        bModifiedProfileImage = true;
        pickProfileLauncher.launch(null);
    }

    private boolean onProfileImageLongClick(View v) {
        profileImageView.setImageResource(R.drawable.profile_img_empty);
        return true;
    }

    private void onPickProfileActivityResult(Bitmap result) {
        if (result != null) {
            profileImageView.setImageBitmap(result);
        }
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
        if (intent.hasExtra(PREF_USERNAME)) {
            String username = intent.getStringExtra(PREF_USERNAME);
            usernameEditText.setText(username);
        }
        if (intent.hasExtra(PREF_PASSWORD)) {
            String password = intent.getStringExtra(PREF_PASSWORD);
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
        // Ensure the username isn't already taken.
        SharedPreferences prefs = getApplication().getSharedPreferences(username, MODE_PRIVATE);
        if (prefs.getBoolean(PREF_ALREADY_EXISTS, false)) {
            showErrorDialog(this, "Username already taken.");
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
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErrorDialog(this, "Incorrect email format.");
            return;
        }

        String phone = phoneEditText.getText().toString();
        if (phone.length() != 10) {
            showErrorDialog(this, "Phone must contain 10 characters.");
            return;
        }

        String password = passwordEditText.getText().toString();
        if (password.length() < 6) {
            showErrorDialog(this, "Password must contain at least 6 characters.");
            return;
        }

        // Save to to shared prefs.
        SharedPreferences.Editor prefsEd = prefs.edit();

        // Mark these prefs as 'already exist'.
        prefsEd.putBoolean(PREF_ALREADY_EXISTS, true);

        if (bModifiedProfileImage) {
            Bitmap profileBitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
            prefsEd.putString(PREF_PROFILE, Utils.saveBitmapToString(profileBitmap));
        }

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