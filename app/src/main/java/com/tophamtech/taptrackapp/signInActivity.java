package com.tophamtech.taptrackapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class signInActivity extends AppCompatActivity {

    static Context context;
    //Setup view components
    Button registerBtn, signInBtn;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
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

        signInBtn = (Button) findViewById(R.id.signIn);
        registerBtn = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        final signInActivity me = this;

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userData[][] = {{"username", username.getText().toString()}, {"password", password.getText().toString()}};
                rest.restParams signInParams = new rest.restParams(userData, "auth");
                rest.httpPost post = new rest().new httpPost(me);
                post.execute(signInParams);
                //helper.toastMaker(getApplicationContext(),post.execute(userData));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(context, registerActivity.class));
            }
        });
    }

    public void validCreds() {
        helper.toastMaker(context, "Valid Credentials:" + session.getJWT());
        context.startActivity(new Intent(context, homeActivity.class));
    }
    public void invalidCreds(Context context, String title, String message) {
        helper.alertMaker(context, "Titleme", "mesme");
    }
}
