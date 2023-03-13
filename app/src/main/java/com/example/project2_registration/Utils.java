package com.example.project2_registration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.appcompat.app.AlertDialog;

import java.io.ByteArrayOutputStream;

public class Utils {

    /** Deserializes an encoded string holding an image's bytes to a bitmap. */
    public static Bitmap loadBitmapFromString(String encodedBytes) {
        byte[] bytes = Base64.decode(encodedBytes, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /** Serializes a bitmap image to bytes, then converts it to a string. */
    public static String saveBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream);
        byte[] bytes = byteStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

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
