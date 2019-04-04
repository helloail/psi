package com.example.ail.psi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TextInputEditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ail.psi.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class updateLahan extends AppCompatActivity {
    private ProgressDialog pDialog;
    ConnectivityManager conMgr;
    private ImageButton btntambahperiode;
    private EditText txtnamalahan;
    private EditText txtletaklahan,panen;
    private Button btnupdatelahan;
    private Context context;
    private int count;
    private TextView txtperiode, txtpanen;
    private RadioButton btnaktif;
    private RadioButton rbt, rba;
    AlertDialog.Builder dialogpanen;
    LayoutInflater inflater;
    View dialogView;
    int success,periodex;
    String id_lahan, tampilpanen;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_NAMA      = "NAMA_LAHAN";
    private static final String TAG_IDLAHAN    = "ID_LAHAN";
    private static final String TAG_IDPEMBUDIDAYA ="ID_PEMBUDIDAYA";
    private static final String TAG_ALAMAT = "LETAK_LAHAN";
    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_PERIODE = "CURRENT_PERIODE";


    private static final String TAG = updateLahan.class.getSimpleName();

    private String url_edit = Server.URL + "editlahan.php";
    private String url_panen = Server.URL + "updatepanen.php";
    private String url_periodepanen = Server.URL + "updatepanentambahperiode.php";
    private String url_update = Server.URL + "updatelahan.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public updateLahan() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters



    public void  onCreate( Bundle savedInstanceState) {
        //layout untuk fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_lahan_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id_lahan = getIntent().getStringExtra("idl");
        setTitle(id_lahan);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


       // new myikc().execute();

        btntambahperiode = (ImageButton) findViewById(R.id.tambah);
        txtnamalahan = (EditText) findViewById(R.id.namalahan);
        txtletaklahan = (EditText) findViewById(R.id.letaklahan);
        btnupdatelahan = (Button) findViewById(R.id.btnupdatelahan);
        txtperiode = (TextView) findViewById(R.id.txtnilai);
        txtpanen = (TextView) findViewById(R.id.panen);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

//        txtperiode.setText("" + count);
        btntambahperiode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogpanen();
            }
        });

        btnupdatelahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tampilpanen != null){
                    panen();

                } else {
                    myikc();
                }
            }
            });

        edit();

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // TODO: Rename method, update argument and hook method into UI event


    private void myikc () {


                // do something

        final   String namalahan =txtnamalahan.getText().toString();
        final   String letaklahan=txtletaklahan.getText().toString();
        final String priode=txtperiode.getText().toString();
        final int status = ((RadioGroup) findViewById(R.id.statuslahan)).getCheckedRadioButtonId();
        final RadioButton rbstatus = ((RadioButton) findViewById(status));
        final String sdrbstatus = rbstatus.getText().toString();

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
                                Toast.makeText(updateLahan.this,
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                onBackPressed();

                            } else {
                                Toast.makeText(updateLahan.this,
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(updateLahan.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah

                params.put("id",id_lahan);
                params.put(TAG_NAMA, namalahan);
                params.put(TAG_ALAMAT, letaklahan);
                params.put(TAG_PERIODE,priode);
                params.put(TAG_STATUS,sdrbstatus);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(updateLahan.this);
        requestQueue.add(strReq);
    }

       private void edit (){

        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {
            final RadioButton rba = (RadioButton) findViewById(R.id.radioButtonAktif);
            final RadioButton rbt = (RadioButton) findViewById(R.id.radioButtonTidakAktif);

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String id = jObj.getString(TAG_IDLAHAN);
                        String statusx = jObj.getString(TAG_STATUS);


                        txtnamalahan.setText(jObj.getString(TAG_NAMA));
                        txtletaklahan.setText(jObj.getString(TAG_ALAMAT));
                       periodex = jObj.getInt(TAG_PERIODE);
                       txtperiode.setText(String.valueOf(periodex));
//                        panen.setText(String.valueOf(jObj.getString(TAG_PERIODE)));
                        if(statusx.equals("Aktif"))
                        {
                            rba.setChecked(true);
                        } else {
                            rbt.setChecked(true);
                        };

                    } else {
                        Toast.makeText(updateLahan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(updateLahan.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post webservice
                Map<String, String> params = new HashMap<String, String>();
                params.put("idlah", id_lahan);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(updateLahan.this);
        requestQueue.add(strReq);
    }



    private void panen () {

        // do something
        tampilpanen= sharedpreferences.getString("hasilpanen" , null);

        final  String periode = String.valueOf(periodex);


        StringRequest strReq = new StringRequest(Request.Method.POST, url_panen,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response panen: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            // Cek error pada json
                            if (success == 1) {
                                myikc();
                                insertpriodepanen();

                                Toast.makeText(updateLahan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(updateLahan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(updateLahan.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah


                params.put("idl",id_lahan);
                params.put("hasilpanen", tampilpanen);
                params.put("priodeO", periode);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(updateLahan.this);
        requestQueue.add(strReq);
    }



    public void dialogpanen(){

        dialogpanen = new AlertDialog.Builder(updateLahan.this);

        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_panen, null);
        dialogpanen.setView(dialogView);
        dialogpanen.setTitle("Masukkan Hasil Panen");
        dialogpanen.setCancelable(false);

        panen = (EditText) dialogView.findViewById(R.id.panen);


        dialogpanen.setPositiveButton("simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogpanen, int which) {
                final String setpanen = panen.getText().toString();


                if (setpanen.isEmpty()) {
                    panen.setError("Kolom Tidak Boleh Kosong");
                    panen.requestFocus();
                } else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("hasilpanen", setpanen);
                    editor.commit();
                    count();

                    tampilpanen= sharedpreferences.getString("hasilpanen" , null);
//                    setTitle(tampilpanen);
                    if(!tampilpanen.isEmpty()) {
                        txtpanen.setText(tampilpanen + "  Kg");
                    }
                }
//                setTitle(tampilpanen);
            }
        });

        dialogpanen.setNegativeButton("Belum Panen", null);
        dialogpanen.show();
    }



    private void insertpriodepanen () {

        // do something
        tampilpanen= sharedpreferences.getString("hasilpanen" , null);


        final String priode=txtperiode.getText().toString();
//        setTitle(String.valueOf(priode));


        StringRequest strReq = new StringRequest(Request.Method.POST, url_periodepanen,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response tambah periode: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            // Cek error pada json
                            if (success == 1) {


                                Toast.makeText(updateLahan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(updateLahan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(updateLahan.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah

                params.put("idl",id_lahan);
                params.put("priodeU", priode);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(updateLahan.this);
        requestQueue.add(strReq);
    }


    private void count() {
        count = periodex;
        count++;
        txtperiode.setText("" + count);
    }
}