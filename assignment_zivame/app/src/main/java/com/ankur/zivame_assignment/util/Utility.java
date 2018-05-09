package com.ankur.zivame_assignment.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ankur on 10/5/18.
 *
 */

public class Utility {
    /**
     * <p>showAlert - display a alert when any event clicked</p>
     * @param context from where its callingcalling
     * @param title will alert title on top
     * @param message will get message text
     */
    public static void showAlert(Context context, String title, String message, String buttonText) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Positive button Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,buttonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
