package com.tophamtech.taptrackapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jamestopham on 08/08/17.
 */
public class helper {
    public static void toastMaker(Context context, String showMsg) {
        Toast.makeText(context, showMsg, Toast.LENGTH_LONG).show();
    }
}
