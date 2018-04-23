package com.tophamtech.taptrackapp;
/*
Icons made by https://www.flaticon.com/authors/chris-veigt
Icons made by https://www.flaticon.com/authors/smashicons
 */


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;


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
    public static TextView firstCard, otherUser;
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

    private int findIcon(String targetName) {
        switch (targetName) {
            case "bin":
                return R.drawable.ic_trash;
            case "bath":
                return R.drawable.ic_bathtub;
            case "washingup":
                return R.drawable.ic_dishwasher;
            case "fix":
                return R.drawable.ic_fixing;
            case "iron":
                return R.drawable.ic_iron;
            case "cooking":
                return R.drawable.ic_oven;
            case "tidy":
                return R.drawable.ic_tidy;
            case "hoover":
                return R.drawable.ic_vacuum_cleaner;
            case "washing":
                return R.drawable.ic_washing_machine;
            default :
                return R.drawable.ic_question_mark;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView =  inflater.inflate(R.layout.fragment_widget, container, false);
        GridLayout gLayout = (GridLayout) fragView.findViewById(R.id.gridFrag);

        Set<String> targetSet = this.getArguments().keySet();
        String[] targetArray = targetSet.toArray(new String[targetSet.size()]);
        String finalTarget = targetArray[0].split("%%")[1];

        Map<String, String> map = (Map<String, String>) this.getArguments().getSerializable("currentTarget%%"+finalTarget);

        gLayout.setRowCount(map.size());
        gLayout.setColumnCount(2);

        //Format the image view
        View targetImage = fragView.findViewById(R.id.targetImage);
        GridLayout.LayoutParams imageParams = new GridLayout.LayoutParams();
        imageParams.leftMargin = imageParams.topMargin = imageParams.bottomMargin = 10;
        imageParams.columnSpec = GridLayout.spec(0,1,0.2f);
        imageParams.rowSpec = GridLayout.spec(0,map.size());
        targetImage.setLayoutParams(imageParams);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (100 * scale + 0.5f);
        targetImage.setMinimumHeight(pixels);

        targetImage.setBackgroundResource(findIcon(finalTarget));

        //Format the text
        int rowCount = 0;
        for(Map.Entry<String, String> entry : map.entrySet()) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            String key = entry.getKey();
            String value = entry.getValue();

            TextView firstCard = new TextView(getContext());
            params.columnSpec = GridLayout.spec(1, 1f);
            params.rowSpec = GridLayout.spec(rowCount);
            params.setGravity(Gravity.FILL_HORIZONTAL);

            firstCard.setPadding(3, 15, 3, 3);
            firstCard.setText("  " +  key.substring(0, 1).toUpperCase() + key.substring(1) + " : " + value);
            firstCard.setTextAppearance(R.style.smallText);
            firstCard.setLayoutParams(params);
            gLayout.addView(firstCard);
            rowCount=rowCount+1;
        }

        return fragView;
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
