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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        if (findViewById(R.id.fragmentHolder) != null) {
            if (savedInstanceState != null) {
                return;
            }

            //Put all data in initial map
            Iterator<String> it = data.keys();
            Map<String, String> map = new HashMap<String,String>();
            ArrayList<String> targetList = new ArrayList<String>();
            while (it.hasNext()){
                String key,value=null;
                key = it.next();
                try {
                    value = data.getString(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //create targetList full of targets
                if (!targetList.contains(key.split("%%")[0])){
                    targetList.add(key.split("%%")[0]);
                }
                map.put(key,value);
            }

            Bundle bundle = new Bundle();
            for (String target:targetList) {
                Map<String, String> localMap = new HashMap<String, String>();
                for (String key : map.keySet()) {
                    if (key.split("%%")[0].equals(target)) {
                        localMap.put(key.split("%%")[1], map.get(key));
                    }
                }
                bundle.putSerializable(target,(Serializable)localMap);
            }
                bundle.putSerializable("targets",targetList);

            //Needs a code cleanup but...putting everything useful in a global bundle
            //then calling the fragment multiple times with a local bundle made of parts of global bundle
            int rowCounter = 1;
            LinearLayout fragHolder = (LinearLayout) findViewById(R.id.fragmentHolder);
            for (String target:targetList) {
                //create new row for next card fragment
                LinearLayout row = new LinearLayout(this);
                //Setup row layout
                LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(llParams);
                //noinspection ResourceType
                row.setId(rowCounter);

                fragHolder.addView(row);
                Bundle localBundle = new Bundle();
                localBundle.putSerializable("currentTarget",bundle.getSerializable(target));
                // Create a new Fragment to be placed in the activity layout
                widgetFragment firstFragment = new widgetFragment();
                firstFragment.setArguments(localBundle);
                //noinspection ResourceType
                getSupportFragmentManager().beginTransaction()
                        .add(rowCounter, firstFragment).commit();
                rowCounter = rowCounter+1;
            }

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
