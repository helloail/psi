package com.example.ail.psi;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class History extends Fragment {


        public History() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.history_fragment, container, false);
            getActivity().setTitle("History");

            return v;
        }

        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }


}
