package com.tophamtech.taptrackapp;

import android.util.Log;

/**
 * Created by jamestopham on 10/08/17.
 */
public class session {
        static String jwtMessage;
        public static void setJWT(String msg){
            jwtMessage = msg;
            Log.d("tester", jwtMessage);
        }
        public static String getJWT(){
            return jwtMessage;
        }
}
