package com.example.ail.psi;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Philipus on 12/11/2016.
 */

public class lahan extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "username";

    // TODO: Rename and change types of parameters
    private String mParam1;
    String mParam2;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;

    String txtmp1;

    private OnFragmentInteractionListener mListener;

    public lahan() {
    }

    // TODO: Rename and change types and number of parameters
    public static lahan newInstance(String param1, String param2) {
        lahan fragment = new lahan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Lahan");

        View x =  inflater.inflate(R.layout.lahan_tab_fragment,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabbaru);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return x;
    }


    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = null;
            switch (position){
                case 0 :
                    fragment = new listLahan();
                    Bundle bundl = new Bundle();
                    bundl.putString("id", mParam1);
                    bundl.putString("username",mParam2);
                    fragment.setArguments(bundl);
                    return fragment;

                case 1 :
                    fragment = new tambahLahan();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mParam1);
                    fragment.setArguments(bundle);
                    return fragment;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "List Lahan";
                case 1 :
                    return "Tambah Lahan";
            }
            return null;
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}