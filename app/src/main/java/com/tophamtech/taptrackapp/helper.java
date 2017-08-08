package com.tophamtech.taptrackapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by jamestopham on 08/08/17.
 */
public class helper {
    public static void toastMaker(Context context, String showMsg) {
        Toast.makeText(context, showMsg, Toast.LENGTH_LONG).show();
    }


    public class httpPost extends AsyncTask<Void, Void, Void> {

        private void streamToString(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("fans",result.toString());
        }

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            try {
                url = new URL("http://tophamtech.noip.me:3000/api/auth/authenticate");
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
                serverConnection.connect();

                //Once the connection is open, write the body
                OutputStreamWriter writer = new OutputStreamWriter(serverConnection.getOutputStream(), "UTF-8");
                writer.write("username=u&password=p");
                writer.close();

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
