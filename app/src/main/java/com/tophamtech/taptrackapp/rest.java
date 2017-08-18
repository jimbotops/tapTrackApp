package com.tophamtech.taptrackapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by jamestopham on 09/08/17.
 */
public class rest {

    public static final String auth = "http://tophamtech.noip.me:3000/api/auth/authenticate";
    public static final String setup = "http://tophamtech.noip.me:3000/api/auth/setup";
    public static final String data = "http://tophamtech.noip.me:3000/api/data?tar=targetC";

    public class httpPost extends AsyncTask<String[][], Void, String> {

        private Context mContext;

        public httpPost(Context context) {
            mContext = context;
        }

        private String streamToString(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
        @Override
        protected String doInBackground(String[][]... params) {
            URL url = null;
            try {
                url = new URL(rest.auth);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection serverConnection = null;
            try {
                serverConnection = (HttpURLConnection) url.openConnection();
                serverConnection.setDoOutput(true);
                serverConnection.setRequestMethod("POST");
                serverConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                Log.d("me", "startConnect");
                serverConnection.connect();
                Log.d("me", "endConnect");
                //Once the connection is open, write the body
                OutputStreamWriter writer = new OutputStreamWriter(serverConnection.getOutputStream(), "UTF-8");
                writer.write(params[0][0][0]+"="+params[0][0][1]+"&"+params[0][1][0]+"="+params[0][1][1]);
                writer.close();

                //read back the server response
                InputStream in = new BufferedInputStream(serverConnection.getInputStream());
                return streamToString(in);

            } catch (ProtocolException e) {
                Log.d("me","fall1");
                signInActivity.validCreds();
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("me","fall2");
                signInActivity signIn = new signInActivity();
                signIn.invalidCreds(mContext, "Error","It appears we're having some technical difficulties.");
                e.printStackTrace();
                Log.d("me", e.toString());
            } finally {
                serverConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                switch (jObject.getString("id")){
                    case "100":
                        session.setJWT(jObject.getString("token"));
                        signInActivity.validCreds();
                        break;
                    default:
                        signInActivity signIn = new signInActivity();
                        signIn.invalidCreds(mContext, "Error","Incorrect username or password");
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class httpGet extends AsyncTask<Void, Void, Void> {
        private void streamToString(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("fans", result.toString());
        }
        @Override
        protected Void doInBackground(Void... params) {

            URL url = null;
            try {
                url = new URL(rest.data);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection serverConnection = null;
            try {
                serverConnection = (HttpURLConnection) url.openConnection();
                serverConnection.setRequestMethod("GET");
                serverConnection.setRequestProperty("x-access-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyIkX18iOnsic3RyaWN0TW9kZSI6dHJ1ZSwic2VsZWN0ZWQiOnt9LCJnZXR0ZXJzIjp7fSwiX2lkIjoiNTk2ZDMxOTA3NzQyMjEyY2U4MDgxNDYxIiwid2FzUG9wdWxhdGVkIjpmYWxzZSwiYWN0aXZlUGF0aHMiOnsicGF0aHMiOnsiX192IjoiaW5pdCIsImdyb3VwQ29kZSI6ImluaXQiLCJncm91cCI6ImluaXQiLCJwYXNzd29yZCI6ImluaXQiLCJ1c2VybmFtZSI6ImluaXQiLCJfaWQiOiJpbml0In0sInN0YXRlcyI6eyJpZ25vcmUiOnt9LCJkZWZhdWx0Ijp7fSwiaW5pdCI6eyJfX3YiOnRydWUsImdyb3VwQ29kZSI6dHJ1ZSwiZ3JvdXAiOnRydWUsInBhc3N3b3JkIjp0cnVlLCJ1c2VybmFtZSI6dHJ1ZSwiX2lkIjp0cnVlfSwibW9kaWZ5Ijp7fSwicmVxdWlyZSI6e319LCJzdGF0ZU5hbWVzIjpbInJlcXVpcmUiLCJtb2RpZnkiLCJpbml0IiwiZGVmYXVsdCIsImlnbm9yZSJdfSwicGF0aHNUb1Njb3BlcyI6e30sImVtaXR0ZXIiOnsiZG9tYWluIjpudWxsLCJfZXZlbnRzIjp7fSwiX21heExpc3RlbmVycyI6MH19LCJpc05ldyI6ZmFsc2UsIl9kb2MiOnsiX192IjowLCJncm91cENvZGUiOiJjIiwiZ3JvdXAiOiJnIiwicGFzc3dvcmQiOiIkMmEkMTAkRHFhVWFQWS85SWlYOExHZjJRdGpZLnQ4Q0lVbUdmdkN1SHZkVlZPeHpuLkhKUnJxZEZwQ1MiLCJ1c2VybmFtZSI6InUiLCJfaWQiOiI1OTZkMzE5MDc3NDIyMTJjZTgwODE0NjEifSwiJGluaXQiOnRydWUsImlhdCI6MTUwMjMwODkwNywiZXhwIjoxNTAyMzEwMzQ3fQ.n_zDaPUZ0HaiBEdMvVRrv6hJTBSR5RC7_3KNt7ht7Ro");
                serverConnection.connect();

                //read back the server response
                InputStream in = new BufferedInputStream(serverConnection.getInputStream());
                streamToString(in);

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverConnection.disconnect();
            }
            return null;
        }
    }
}
