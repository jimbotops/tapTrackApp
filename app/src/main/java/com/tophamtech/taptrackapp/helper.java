package com.tophamtech.taptrackapp;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by jamestopham on 08/08/17.
 */
public class helper extends Application{
    public static void toastMaker(Context context, String showMsg) {
        Toast.makeText(context, showMsg, Toast.LENGTH_LONG).show();
    }


    public static void alertMaker(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
