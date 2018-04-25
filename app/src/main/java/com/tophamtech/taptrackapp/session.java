package com.tophamtech.taptrackapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;


import com.auth0.android.jwt.JWT;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by jamestopham on 10/08/17.
 */
public class session {
        static String jwtMessage;
        public static void setJWT(String jwt, Context context){
            jwtMessage = jwt;
            Log.d("tester", jwt);
            SharedPreferences sharedpreferences = context.getSharedPreferences("localStorage", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("jwt",jwt);
            editor.commit();
        }

        public static void clearJWT(Context context){
            String jwt = "no_jwt";
            jwtMessage = jwt;
            SharedPreferences sharedpreferences = context.getSharedPreferences("localStorage", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("jwt", jwt);
            editor.commit();
        }
        public static String getJWT(Context context){
            SharedPreferences sharedpreferences = context.getSharedPreferences("localStorage", Context.MODE_PRIVATE);
            String jwtStartup = sharedpreferences.getString("jwt", null);
            jwtMessage = jwtStartup;
            return jwtMessage;
        }

        public static JSONObject getDecodedJWT(Context context) throws Exception {
            String JWTEncoded = getJWT(context);
            String[] jwtSplit = JWTEncoded.split("\\.");
            byte[] data = Base64.decode(jwtSplit[1], Base64.DEFAULT);
            String decoded = new String(data);
            JSONObject jOb = new JSONObject(decoded);
            JSONObject docObject = jOb.getJSONObject("_doc");
//            String username = docObject.getString("username");
            return  (docObject);
        }
}
