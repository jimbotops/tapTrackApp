package com.tophamtech.taptrackapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link widgetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link widgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class widgetFragment extends Fragment {

    public static Button tester;
    public static TextView firstUser, otherUser;
    private OnFragmentInteractionListener mListener;

    public static widgetFragment newInstance() {
        widgetFragment fragment = new widgetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public widgetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView =  inflater.inflate(R.layout.fragment_widget, container, false);
        GridLayout gLayout = (GridLayout) fragView.findViewById(R.id.gridFrag);
        Map<String, String> map = (Map<String, String>) this.getArguments().getSerializable("map");

        gLayout.setRowCount(map.size());
        gLayout.setColumnCount(2);

        int rowCount = 0;
        for(Map.Entry<String, String> entry : map.entrySet()) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            String key = entry.getKey();
            String value = entry.getValue();

            TextView firstUser = new TextView(getContext());
            params.columnSpec = GridLayout.spec(1, 1f);
            params.rowSpec = GridLayout.spec(rowCount);
            params.setGravity(Gravity.FILL_HORIZONTAL);

            //firstUser.setPadding(3, 3, 3, 3);
            firstUser.setText(key + " - " + value);
            firstUser.setBackgroundColor(Color.RED);
            firstUser.setLayoutParams(params);
            gLayout.addView(firstUser);
            rowCount=rowCount+1;
        }


        return fragView;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            Toast.makeText(getContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            tester.setText("changed");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
