package com.example.ail.psi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ail.psi.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class monitoring extends AppCompatActivity {

    public static final String TAG_IDl = "idl";
    private String urlmon = Server.URL + "tambahmonitoring.php";
    private String url = Server.URL + "tambahkondisilahan.php";
    private String urlsal = Server.URL + "tambahkondisisalinitas.php";
    private String urlamo = Server.URL + "tambahkondisiamonia.php";
    private String urlnit = Server.URL + "tambahkondisinitrit.php";
    private String urlph = Server.URL + "tambahkondisiph.php";
    private String urlsuhu = Server.URL + "tambahkondisisuhu.php";
    private String urlkec = Server.URL + "tambahkondisikecerahan.php";


    private static final String TAG = monitoring.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";


    String id_lahan,periode,namalahan;
    int success;
    Intent intent;

    private EditText txtdo;
    private EditText txtamonia;
    private EditText txtids;
    private EditText txtnitrit;
    private EditText txtph;
    private EditText txtsuhu;
    private EditText txtkecerahan;
    private EditText txtsalinitas;
    private TextView tes;
    private Button generate;
    private ProgressDialog pDialog;
//    String disoksi,salinitas,amonia,nitrit,ph,suhu,kecerahan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_fragment);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id_lahan = getIntent().getStringExtra(TAG_IDl);
        periode = getIntent().getStringExtra("periode");
        namalahan = getIntent().getStringExtra("nama");

        txtids       = (EditText) findViewById(R.id.txt_ids);
        txtdo     = (EditText) findViewById(R.id.linierDO);
        txtamonia  = (EditText) findViewById(R.id.linierAmonia);
        txtnitrit      = (EditText) findViewById(R.id.linierNitrit);
        txtph    = (EditText) findViewById(R.id.linierPH);
        txtsuhu  = (EditText) findViewById(R.id.linierSuhu);
        txtkecerahan    = (EditText) findViewById(R.id.linierkecerahan);
        txtsalinitas  = (EditText) findViewById(R.id.linierSalinitas);
        generate = (Button) findViewById(R.id.buttongenerate);
        txtids = (EditText) findViewById(R.id.txt_ids);
        setTitle(namalahan);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




        generate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String idlah = txtids.getText().toString();

                final String disoksi = (txtdo.getText().toString());
                final float ido = !disoksi.equals("")?Float.parseFloat(disoksi) : 0;
//                final int ido = Integer.valueOf(disoksi);
                final String amonia = txtamonia.getText().toString();
                final float iamo = !amonia.equals("")?Float.parseFloat(amonia) : 0;

//                final int iamo = Integer.valueOf(amonia);
                final String nitrit = txtnitrit.getText().toString();
                final float init = !nitrit.equals("")?Float.parseFloat(nitrit) : 0;
//                final int init = Integer.valueOf(nitrit);
                final String ph = txtph.getText().toString();
//                final int iph = Integer.valueOf(ph);
                final float iph = !ph.equals("")?Float.parseFloat(ph) : 0;
                final String suhu = txtsuhu.getText().toString();
//                final int isuhu = Integer.valueOf(suhu);
                final float isuhu = !suhu.equals("")?Float.parseFloat(suhu) : 0;
                final String kecerahan = txtkecerahan.getText().toString();
//                final int ikec = Integer.valueOf(kecerahan);
                final float ikec = !kecerahan.equals("")?Float.parseFloat(kecerahan) : 0;
                final String salinitas = txtsalinitas.getText().toString();
//                final int isal = Integer.valueOf(salinitas);
                final float isal = !salinitas.equals("")?Float.parseFloat(salinitas) : 0;


                if (disoksi.isEmpty())
                {txtdo.setError("Kolom Harus Terisi");

                }
                if(amonia.isEmpty())
                {txtamonia.setError(" Kolom Harus Terisi");

                }
                if(nitrit.isEmpty())
                {txtnitrit.setError("Kolom Harus Terisi");

                }
                if(ph.isEmpty())
                {txtph.setError("Kolom Harus Terisi");

                }

                if(suhu.isEmpty())
                {txtsuhu.setError("Kolom Harus Terisi");

                }
                if(salinitas.isEmpty())
                {txtsalinitas.setError("Kolom Harus Terisi");
                }
                if(kecerahan.isEmpty())
                {
                    txtkecerahan.setError("Kolom Harus Terisi");

                }else

                if(ido <= 0 || ido >= 20 )
                {
                    txtdo.setError("Batas inputan 0 - 20");
                }
                else if(init <= 0 || init >= 4 )
                {
                    txtnitrit.setError("Batas inputan 0 - 4");
                }
                else if(iamo <= 0 || iamo >= 4 )
                {
                    txtamonia.setError("Batas inputan 0 - 4");
                }
                else if(iph <= 3 || iph >= 11 )
                {
                    txtph.setError("Batas inputan 3 - 11");
                }
                else if(isuhu <= 23 || isuhu >= 40 )
                {
                    txtsuhu.setError("Batas inputan 23 - 40");
                }
                else if(ikec <= 10 || ikec >= 150 )
                {
                    txtkecerahan.setError("Batas inputan 10 - 150");
                }
                else if(isal <= 5 || isal >= 45 )
                {
                    txtsalinitas.setError("Batas inputan 5 - 45");
                }
                else {


                    pDialog = new ProgressDialog(monitoring.this);
                    pDialog.setCancelable(false);
                    pDialog.setMessage("Generate ...");
                    pDialog.show();

               StringRequest strReq = new StringRequest(Request.Method.POST, urlmon, new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG, "setmonitoring Response: " + response.toString())
                            ;
                            pDialog.dismiss();
                            try {

                                JSONObject jObj = new JSONObject(response);
                                success = jObj.getInt(TAG_SUCCESS);

                                // Check for error node in json
                                if (success == 1) {

                                    Log.e("Successfully monitoring", jObj.toString());

                                    Toast.makeText(getApplicationContext(),
                                            jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                    kondisisalinitas(salinitas);
                                    kondisiamonia(amonia);
                                    kondisinitrit(nitrit);
                                    kondisiph(ph);
                                    kondisisuhu(suhu);
                                    kondisikecerahan(kecerahan);
                                    kondisido(disoksi);
                                    kosong();

                                   final Handler handler = new Handler();
                                   handler.postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           Intent intent = new Intent(monitoring.this,detailmonitoring.class);
                                           intent.putExtra("idl",id_lahan);
                                           intent.putExtra("namalahan",namalahan);
                                           startActivity(intent);

                                       }
                                   }, 2500);




                                } else {
                                    Toast.makeText(getApplicationContext(),
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
                            pDialog.dismiss();

                            Log.e(TAG, "Error: " + error.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("idlah", id_lahan);
                            params.put("periode", periode);


                            return params;
                        }
                    };

                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
                }
            }
        });

    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void kondisido(
            final String disoksi) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "monitoring Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully do!", jObj.toString());

//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("do", disoksi);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void kondisisalinitas(final String salinitas) {
        StringRequest strReq = new StringRequest(Request.Method.POST, urlsal, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "monitoring salinitas: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("salinitas", salinitas);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void kondisiamonia(final String amonia) {
        StringRequest strReq = new StringRequest(Request.Method.POST, urlamo, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "monitoring amonia: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("amonia", amonia);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


    private void kondisinitrit(final String nitrit) {
        StringRequest strReq = new StringRequest(Request.Method.POST, urlnit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "monitoring nitrit: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nitrit", nitrit);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void kondisiph(final String ph) {
        StringRequest strReq = new StringRequest(Request.Method.POST, urlph, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "monitoring ph: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("ph", ph);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void kondisisuhu(final String suhu) {
        StringRequest strReq = new StringRequest(Request.Method.POST, urlsuhu, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "monitoring suhu: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("suhu", suhu);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void kondisikecerahan(final String kecerahan) {
        StringRequest strReq = new StringRequest(Request.Method.POST, urlkec, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "monitoring kecerahan: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("kecerahan", kecerahan);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


    private boolean kosong(){

        txtdo.setText(null);
        txtsalinitas.setText(null);
        txtkecerahan.setText(null);
        txtph.setText(null);
        txtamonia.setText(null);
        txtnitrit.setText(null);
        txtsuhu.setText(null);


        return  true;
    }


}