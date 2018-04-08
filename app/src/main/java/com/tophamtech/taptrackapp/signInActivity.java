package com.tophamtech.taptrackapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Locale;


// TODO:
// Read tag on this page should call the rest layer to actually update
// Register node needs to return an id not just true/false
// needs better error handling

public class signInActivity extends AppCompatActivity {

    static Context context;
    //Setup view components
    Button registerBtn, signInBtn, button;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_sign_in);

        SharedPreferences sharedpreferences = this.getSharedPreferences("localStorage", Context.MODE_PRIVATE);
        String jwtStartup = sharedpreferences.getString("jwt", "no_jwt");
        if (!jwtStartup.equals("no_jwt")) {
            startActivity(new Intent(context, homeActivity.class));
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        ;

        signInBtn = (Button) findViewById(R.id.signIn);
        registerBtn = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);
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

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, registerActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();
        Log.d("JT","resume");
        Intent intent = getIntent();
        readTag(intent);
    }

    private void readTag(Intent intent) {
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) { // NDEF Message available
                NdefMessage msg =(NdefMessage) rawMsgs[0];
                NdefRecord[] ndefRecords = msg.getRecords();
                String payload = new String(ndefRecords[0].getPayload());
                incrementTarget(payload.substring(3));
            }
        }
    }

    public void incrementTarget(String target) {
        String userData[][] = {{"tar", target}};

        rest.restParams registerParams = new rest.restParams(userData, "increment");
        rest.httpPost post = new rest().new httpPost(this);
        post.execute(registerParams);
    }

    public void validCreds() {
        helper.toastMaker(context, "Login successful!");
        context.startActivity(new Intent(context, homeActivity.class));
    }
    public void invalidCreds(Context context, String title, String message) {
        helper.alertMaker(context, title, message);
    }
}
