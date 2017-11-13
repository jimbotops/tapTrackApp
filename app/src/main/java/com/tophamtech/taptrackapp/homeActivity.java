package com.tophamtech.taptrackapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class homeActivity extends AppCompatActivity
    implements widgetFragment.OnFragmentInteractionListener{



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String initData;
        JSONObject parsedData = null,data = null;
        rest.httpGet getInit = new rest().new httpGet();
        getInit.execute();
        try {
            initData = getInit.get().toString();
            parsedData = new JSONObject(initData);
            data = parsedData.getJSONObject("data");
            //String param = data.getString("sink");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        if (findViewById(R.id.fragmentHolder) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            widgetFragment firstFragment = new widgetFragment();
            Iterator<String> it = data.keys();
            Map<String, String> map = new HashMap<String,String>();
            while (it.hasNext()){
                String key,value=null;
                key = it.next();
                try {
                    value = data.getString(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                map.put(key,value);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("map", (Serializable) map);
            firstFragment.setArguments(bundle);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentHolder, firstFragment).commit();


        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(getApplicationContext(), "In home", Toast.LENGTH_SHORT).show();
    }
}
