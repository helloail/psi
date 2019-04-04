package com.example.ail.psi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ail.psi.adapter.adapterListhistory;
import com.example.ail.psi.adapter.adapterListmonitoring;
import com.example.ail.psi.getset.listhistorylahangetset;
import com.example.ail.psi.getset.listmonitorlahangetset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listlahanhistory extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


        SwipeRefreshLayout swipe;
        Toolbar toolbar;
        FloatingActionButton fab;
        ListView list;
        adapterListhistory adapter;
        int success;
        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;




        private static final String TAG = com.example.ail.psi.listlahanhistory.class.getSimpleName();

        private String url = Server.URL +"listlahanhistory.php";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String ARG_PARAM1 = "id";
        private static final String ARG_PARAM2 = "username";
        private String mParam1;
        private String mParam2;



        List<listhistorylahangetset> itemList = new ArrayList<listhistorylahangetset>();

        public listlahanhistory() {
            // Required empty public constructor
        }

        public static listlahanhistory newInstance(String param1, String param2) {
            listlahanhistory fragment = new listlahanhistory();
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

        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //layout untuk fragment
            View v = inflater.inflate(R.layout.content_listhistory, container, false);

            swipe   = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout_history);
            list    = (ListView) v.findViewById(R.id.listhistory);

            adapter = new adapterListhistory(getActivity(), itemList);
            list.setAdapter(adapter);


            swipe.setOnRefreshListener(this);

            swipe.post(new Runnable() {
                           @Override
                           public void run() {
                               swipe.setRefreshing(true);
                               //        itemList.clear();
                               adapter.notifyDataSetChanged();
                               koneksi();
                           }
                       }
            );

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final String idl = itemList.get(position).getId();

                    Intent intent = new Intent(getContext(),listmonitoring.class);
                    intent.putExtra("idl",idl);
                    startActivity(intent);
                }});


            return v;
        }

        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            //mengubah judul pada toolbar
            getActivity().setTitle("History");
        }

        public void onRefresh() {
            itemList.clear();
            adapter.notifyDataSetChanged();
            koneksi();
        }


        private void koneksi(){
            itemList.clear();
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(true);

            final String KEY_IDLAHAN = "iduser";

            // membuat req array dengan JSON
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d(TAG,"Register Response: " + response.toString());

                    // Parsing json

                    //              JSONArray ja = response.getJSONArray("LAHAN");

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = jsonArray.getJSONObject(i);

                            listhistorylahangetset item = new listhistorylahangetset();
                            item.setNama(obj.getString("NAMA_LAHAN"));
                            item.setID(obj.getString("ID_LAHAN"));

                            // menambah item ke array
                            itemList.add(item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // notifikasi perubahan data adapter
                    adapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    swipe.setRefreshing(false);
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    // Posting parameter ke post url
                    Map<String, String> params = new HashMap<String, String>();
                    // jika id kosong maka simpan, jika id ada nilainya maka ubah
                    params.put(KEY_IDLAHAN, mParam1);
                    return params;
                }};
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }

        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }
    }
