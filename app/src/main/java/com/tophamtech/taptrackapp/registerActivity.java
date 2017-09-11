package com.tophamtech.taptrackapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class registerActivity extends ActionBarActivity {

    Button registerBtn;
    EditText username, password, groupId, groupPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        groupId = (EditText) findViewById(R.id.groupId);
        groupPassword = (EditText) findViewById(R.id.groupPassword);

        final registerActivity me = this;
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userData [][] = {{"username", username.getText().toString()}, {"password", password.getText().toString()},
                        {"groupId", groupId.getText().toString()}, {"groupPassword", groupPassword.getText().toString()}};

                rest.restParams registerParams = new rest.restParams(userData, "setup");

                rest.httpPost post = new rest().new httpPost(me);
                post.execute(registerParams);
            }
            });
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
