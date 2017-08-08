package com.tophamtech.taptrackapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class register extends AppCompatActivity {

    //Setup view components
    Button register;
    EditText username, password;
    //helper myHelper = new helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        register = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //makes a new instance of the class
                webFetch downloadTask = new webFetch();
                //starts the task
                downloadTask.execute();
                Log.d("personal", "reach2");
                Toast.makeText(getApplicationContext(), "Clicked button by: " + username.getText() + password.getText(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public class webFetch extends AsyncTask<String, Void, String> {
        TextView textBox1;
        String gbl_str = null;

        public webFetch() {
            this.textBox1 = textBox1;
        }

        //doinbackground is keywords to be async
        protected String doInBackground(String... urls) {
            String pathToFile = "http://jimbotops.github.io/facts.txt";
            String bitmap = null;
            Log.d("personal","reach1");
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                Log.d("personal","reach3");
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                bitmap = total.toString();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        //this is autocalled after async completion
        protected void onPostExecute(String result) {
            //textBox1.setText(result);
            Log.d("personal","reach4");
            gbl_str = result;
            popTextBox();

        }

        public void popTextBox() {
            Log.d("personal", "reach5");
            //Toast.makeText(getApplicationContext(), gbl_str, Toast.LENGTH_LONG).show();
            helper.toastMaker(getApplicationContext(),gbl_str);
            helper.httpPost myPost = new helper().new httpPost();
            myPost.execute();

        }
    }
}
