package com.example.project2_registration;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Utils {

    /**
     * Creates a simple dialog to show an error message.
     */
    public static void showErrorDialog(Context context, String errorMessage) {
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("Okay", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
