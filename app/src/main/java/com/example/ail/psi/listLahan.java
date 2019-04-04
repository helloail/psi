package com.example.ail.psi;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;


import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ail.psi.adapter.Adapter;
import com.example.ail.psi.app.AppController;
import com.example.ail.psi.getset.updateLahangetset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class listLahan extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;
    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    Adapter adapter;
    int success;
    AlertDialog.Builder dialog,dialogpanen;
    LayoutInflater inflater;
    View dialogView;
    String id,namalahan,letaklahan,priode,status;


    private ImageButton btntambahperiode;
    private EditText txtnamalahan;
    private EditText txtletaklahan;
    private EditText txtids;
    private Button btntambahlahan;
    private Context context;
    private int count;
    private TextView txtperiode;
    private RadioButton btnaktif;

    AlertDialog alertDialog;
//    String id, judul, isi;


    List<updateLahangetset> itemList = new ArrayList<updateLahangetset>();

    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "username";

    // TODO: Rename and change types of parameters
    private String mParam1;
    String mParam2;

    private static final String TAG = listLahan.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_NAMA      = "NAMA_LAHAN";
    private static final String TAG_IDLAHAN    = "ID_LAHAN";
    private static final String TAG_IDPEMBUDIDAYA ="ID_PEMBUDIDAYA";
    private static final String TAG_ALAMAT = "LETAK_LAHAN";
    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_PERIODE = "CURRENT_PERIODE";


    private String url = Server.URL + "listlahanupdate.php";
    private String url_edit = Server.URL + "editlahan.php";
    private String url_delete = Server.URL + "deletelahan.php";
    private String url_update = Server.URL + "updatelahan.php";


    private OnFragmentInteractionListener mListener;

    public listLahan() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static listLahan newInstance(String param1, String param2) {
        listLahan fragment = new listLahan();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_listupdate,container, false);
        swipe   = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) v.findViewById(R.id.list);

        adapter = new Adapter(getActivity(), itemList);
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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(getContext());
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

//                                edit(idx);
//                                break;
                                Intent intent = new Intent(getContext(),updateLahan.class);
                                intent.putExtra("idl",idx);
                                startActivity(intent);
                                break;
                            case 1:
                                delete(idx);
                                break;
                        }
                    }
                    }
                ).show();
            }
        });

        return v;
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        koneksi();
    }

    // untuk clear edittext pada form
    private void kosong(){
        txtnamalahan.setText(null);
        txtnamalahan.setText(null);
        txtids.setText(null);
    }

//    private void count() {
//        count = String.valueOf(periode);
//        count++;
//        txtperiode.setText( "" + count);
//    };


    // untuk menampilkan dialog catatan
    private void DialogForm(String idx, String nama, String alamat, final int periode, String statusl, final String button) {
        dialog = new AlertDialog.Builder(getContext());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.update_lahan_fragment, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
//        dialog.setTitle("Catatan");

        txtids =  (EditText) dialogView.findViewById(R.id.txt_ids);
        btntambahperiode  = (ImageButton) dialogView.findViewById(R.id.tambah);
        txtnamalahan = (EditText) dialogView.findViewById(R.id.namalahan);
        txtletaklahan = (EditText) dialogView.findViewById(R.id.letaklahan);
        btntambahlahan = (Button) dialogView.findViewById(R.id.btntambahlahan);
        txtperiode = (TextView) dialogView.findViewById(R.id.txtnilai);



//        int status = ((RadioGroup) dialogView.findViewById(R.id.statuslahan)).getCheckedRadioButtonId();
//        final RadioButton rbstatus = ((RadioButton) getView().findViewById(status));
        final RadioButton rba = (RadioButton) dialogView.findViewById(R.id.radioButtonAktif);
        final RadioButton rbt = (RadioButton) dialogView.findViewById(R.id.radioButtonTidakAktif);
//        final RadioButton rbstatus = ((RadioButton) dialogView.findViewById(R.id.statuslahan));


//        txt_id      = (EditText) dialogView.findViewById(R.id.txt_ids);
//        txt_judul    = (EditText) dialogView.findViewById(R.id.txt_juduls);
//        txt_isi  = (EditText) dialogView.findViewById(R.id.txt_isis);

        if (!idx.isEmpty()){
            txtids.setText(idx);
            txtnamalahan.setText(nama);
            txtletaklahan.setText(alamat);
            txtperiode.setText(String.valueOf(periode));
            btntambahperiode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogpanen = new AlertDialog.Builder(getContext());

                    inflater = getLayoutInflater();
                    dialogView = inflater.inflate(R.layout.dialog_panen, null);
                    dialogpanen.setView(dialogView);
                    dialogpanen.setTitle("Masukkan Hasil Panen");
                    dialogpanen.setCancelable(false);

                    final EditText panen = (EditText) dialogView.findViewById(R.id.panen);
                    final String setpanen = panen.getText().toString();
                    getActivity().setTitle(String.valueOf(setpanen));

                    dialogpanen.setPositiveButton("simpan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogpanen, int which) {

                            if(!setpanen.isEmpty() ){
                                count = periode;
                            count++;
                            txtperiode.setText( "" + count);

                            }
//                          else{
//                                panen.setError("Kolom Tidak Boleh Kosong");
//                                panen.requestFocus();
//                            }

                        }
                    });


                    dialogpanen.setNegativeButton("Belum Panen", null);
                    dialogpanen.show();

                }
            });

            if(statusl.equals("Aktif"))
            {
                rba.setChecked(true);
            } else {
                rbt.setChecked(true);
            };

        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String id      = txtids.getText().toString();

               final   String namalahan =txtnamalahan.getText().toString();
               final   String letaklahan=txtletaklahan.getText().toString();
               final String priode=txtperiode.getText().toString();
               final String status = rba.getText().toString();
                 getActivity().setTitle(status + letaklahan + status);

                StringRequest strReq = new StringRequest(Request.Method.POST, url_update,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            // Cek error pada json
                            if (success == 1) {
                                Log.d("Add/update", jObj.toString());
                                koneksi();
                                kosong();
                                Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameter ke post url
                        Map<String, String> params = new HashMap<String, String>();
                        // jika id kosong maka simpan, jika id ada nilainya maka ubah

                        params.put("id",id);
                        params.put(TAG_NAMA, namalahan);
                        params.put(TAG_ALAMAT, letaklahan);
                        params.put(TAG_PERIODE,priode);
                        params.put(TAG_STATUS,status);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(strReq);


//                simpan();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();
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
        if (context instanceof tambahLahan.OnFragmentInteractionListener) {
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
        //  TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

                        updateLahangetset item = new updateLahangetset();

                        item.setNama(obj.getString("NAMA_LAHAN"));
                        item.setID(obj.getString("ID_LAHAN"));
                        item.setCreatedon(obj.getString("CREATED_ONLAHAN"));

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




    private void edit(final String idx){

        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String id      = jObj.getString(TAG_IDLAHAN);
                        String namax    = jObj.getString(TAG_NAMA);
                        String alamatx  = jObj.getString(TAG_ALAMAT);
                        int periodex = jObj.getInt(TAG_PERIODE);
                        String statusx = jObj.getString(TAG_STATUS);

                        DialogForm(id, namax, alamatx, periodex, statusx, "UBAH");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post webservice
                Map<String, String> params = new HashMap<String, String>();
                params.put("idlah", idx);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void delete(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());

                        koneksi();

                        Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }}
            )
        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }
}