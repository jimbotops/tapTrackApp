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
    public static final String init = "http://tophamtech.noip.me:3000/api/data/init";

    public static class restParams {
        String[][] data;
        String url;

        restParams(String[][] data, String url) {
            this.data = data;
            this.url = url;
        }
    }

    public class httpPost extends AsyncTask<restParams, Void, String> {

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
        protected String doInBackground(restParams... params) {
            URL url = null;
            try {
                switch (params[0].url){
                    case "auth" :
                        url = new URL(rest.auth);
                        break;
                    case "setup" :
                        url = new URL(rest.setup);
                        break;

                }

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
                StringBuilder sbData = new StringBuilder();
                for (int i=0;i<params[0].data.length;i++)
                {
                    if (i!=0){
                        sbData.append("&");
                    }
                    sbData.append(params[0].data[i][0] + "=" + params[0].data[i][1]);
                }
                //writer.write(params[0][0][0]+"="+params[0][0][1]+"&"+params[0][1][0]+"="+params[0][1][1]);
                writer.write(sbData.toString());
                writer.close();

                //read back the server response
                InputStream in = new BufferedInputStream(serverConnection.getInputStream());
                return streamToString(in);

            } catch (ProtocolException e) {
                signInActivity signIn = new signInActivity();
                Log.d("me","fall1");
                signIn.validCreds();
                e.printStackTrace();
            } catch (IOException e) {
                signInActivity signIn = new signInActivity();
                Log.d("me","fall2");
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
            signInActivity signIn = new signInActivity();
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
                        signIn.validCreds();
                        break;
                    default:
                        signIn.invalidCreds(mContext, "Error","Incorrect username or password");
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class httpGet extends AsyncTask<Void, Void, String> {
        private String streamToString(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("fans", result.toString());
            return result.toString();
        }
        @Override
        protected String doInBackground(Void... params) {

            URL url = null;
            try {
                url = new URL(rest.init);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection serverConnection = null;
            try {
                serverConnection = (HttpURLConnection) url.openConnection();
                serverConnection.setRequestMethod("GET");
                serverConnection.setRequestProperty("x-access-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyIkX18iOnsic3RyaWN0TW9kZSI6dHJ1ZSwic2VsZWN0ZWQiOnt9LCJnZXR0ZXJzIjp7fSwiX2lkIjoiNWEwYWMxYzlhNTMwNTE0NjRhZjNhODg0Iiwid2FzUG9wdWxhdGVkIjpmYWxzZSwiYWN0aXZlUGF0aHMiOnsicGF0aHMiOnsiX192IjoiaW5pdCIsImdyb3VwQ29kZSI6ImluaXQiLCJncm91cCI6ImluaXQiLCJwYXNzd29yZCI6ImluaXQiLCJ1c2VybmFtZSI6ImluaXQiLCJfaWQiOiJpbml0In0sInN0YXRlcyI6eyJpZ25vcmUiOnt9LCJkZWZhdWx0Ijp7fSwiaW5pdCI6eyJfX3YiOnRydWUsImdyb3VwQ29kZSI6dHJ1ZSwiZ3JvdXAiOnRydWUsInBhc3N3b3JkIjp0cnVlLCJ1c2VybmFtZSI6dHJ1ZSwiX2lkIjp0cnVlfSwibW9kaWZ5Ijp7fSwicmVxdWlyZSI6e319LCJzdGF0ZU5hbWVzIjpbInJlcXVpcmUiLCJtb2RpZnkiLCJpbml0IiwiZGVmYXVsdCIsImlnbm9yZSJdfSwicGF0aHNUb1Njb3BlcyI6e30sImVtaXR0ZXIiOnsiZG9tYWluIjpudWxsLCJfZXZlbnRzIjp7fSwiX21heExpc3RlbmVycyI6MH19LCJpc05ldyI6ZmFsc2UsIl9kb2MiOnsiX192IjowLCJncm91cENvZGUiOiIxMjM0IiwiZ3JvdXAiOiIxMjM0IiwicGFzc3dvcmQiOiIkMmEkMTAkM3Rzcm94MzRrOXZYNHRDNTBDL2JYZW9DOC83ckduLmhjMmo0VFQ2bDBMZjVTMHFDMGN3RzYiLCJ1c2VybmFtZSI6InRlc3R1c2VyMiIsIl9pZCI6IjVhMGFjMWM5YTUzMDUxNDY0YWYzYTg4NCJ9LCIkaW5pdCI6dHJ1ZSwiaWF0IjoxNTEwNzg0MzQ0LCJleHAiOjE1MTA3ODU3ODR9.SQGhhQP05MpyJS4h0ZBmq0A4Cl8l9wNnuJnCVa1vc4Y");
                serverConnection.connect();

                //read back the server response
                InputStream in = new BufferedInputStream(serverConnection.getInputStream());
                return streamToString(in);

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverConnection.disconnect();
            }
            return "Error";
        }
    }
}
