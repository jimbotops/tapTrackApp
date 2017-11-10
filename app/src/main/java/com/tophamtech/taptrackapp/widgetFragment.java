package com.tophamtech.taptrackapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
        firstUser = (TextView) fragView.findViewById(R.id.firstUser);
        otherUser = (TextView) fragView.findViewById(R.id.otherUser);

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
