package it.celli.testgithub.utils;


import android.app.AlertDialog;
import android.content.Context;

import it.celli.testgithub.R;

public class Utils {

    public static String BASE_URL = "https://api.github.com";

    public static void showErrorDialog(Context context, String description, String errorCode) {

        String message = description;
        if(errorCode != null) {
            message += " ("+ errorCode +")";
        }

        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, (dialog, which) -> dialog.cancel())
                .create()
                .show();
    }
}
