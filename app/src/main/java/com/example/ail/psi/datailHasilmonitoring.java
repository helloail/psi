package com.example.ail.psi;

        import android.os.Bundle;

        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;

        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.VolleyLog;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;


        import org.json.JSONException;
        import org.json.JSONObject;


        import java.util.HashMap;
        import java.util.Map;

public class datailHasilmonitoring extends AppCompatActivity {

    Toolbar toolbar;


    public static final String TAG_IDl = "idmon";
    private static final String TAG = com.example.ail.psi.datailHasilmonitoring.class.getSimpleName();

    private String urldo = Server.URL +"detailmonitoringdo.php";
    private String urlmon = Server.URL +"detailhistory.php";
    private String urlamonia = Server.URL +"detailmonitoringamonia.php";
    private String urlnitrit = Server.URL +"detailmonitoringnitrit.php";
    private String urlsuhu = Server.URL +"detailmonitoringsuhu.php";
    private String urlph = Server.URL +"detailmonitoringph.php";
    private String urlkecerahan = Server.URL +"detailmonitoringkecerahan.php";
    private String urlsalinitas = Server.URL +"detailmonitoringsalinitas.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String idmonitoring, tanggalmon;


    int success;
    String id_lahan;

    TextView txtiddo,txtnilaido,txtkondisido,txtsarando,
            txtidamonia,txtnilaiamonia,txtkondisiamonia,txtsaranamonia,
            txtidnitrit,txtnilainitrit,txtkondisinitrit,txtsarannitrit,
            txtidph,txtnilaiph,txtkondisiph,txtsaranph,
            txtidsuhu,txtnilaisuhu,txtkondisisuhu,txtsaransuhu,
            txtidkecerahan,txtnilaikecerahan,txtkondisikecerhan,txtsarankecerahan,
            txtidsalinitas,txtnilaisalinitas,txtkondisisalinitas,txtsaransalinitas,
            setstartperiode,setendperiode,setperiodeke,sethasilpanen,setnamalahan;



    public datailHasilmonitoring() {
        // Required empty public constructor
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailhasil_monitoring);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        id_lahan = getIntent().getStringExtra("idl");
        tanggalmon = getIntent().getStringExtra("tanggalmon");
        setTitle(tanggalmon);





        idmonitoring = getIntent().getStringExtra(TAG_IDl);
        setstartperiode = findViewById(R.id.setstartperiodedetail);
        setendperiode = findViewById(R.id.setendperiodedetail);
        setperiodeke = findViewById(R.id.setperiodedetail);
        sethasilpanen = findViewById(R.id.sethasillahandetail);
        setnamalahan = findViewById(R.id.setnamalahandetail);

        setnamalahan.setText(id_lahan);


        txtiddo = findViewById(R.id.TitleDo);
        txtnilaido = findViewById(R.id.nilaiDO);
//        txtnilaido.setText(nilaido);
        txtkondisido = findViewById(R.id.KondisiDODO);
        txtsarando = findViewById(R.id.SaranDO);

        txtidnitrit = findViewById(R.id.TitleNitrit);
        txtnilainitrit = findViewById(R.id.nilaiNitrit);
        txtkondisinitrit = findViewById(R.id.KondisiNitrit);
        txtsarannitrit = findViewById(R.id.SaranNitrit);

        txtidamonia = findViewById(R.id.TitleAmonia);
        txtnilaiamonia = findViewById(R.id.nilaiAmonia);
        txtkondisiamonia = findViewById(R.id.KondisiAmonia);
        txtsaranamonia = findViewById(R.id.SaranAmonia);

        txtidnitrit = findViewById(R.id.TitleNitrit);
        txtnilainitrit = findViewById(R.id.nilaiNitrit);
        txtkondisinitrit = findViewById(R.id.KondisiNitrit);
        txtsarannitrit = findViewById(R.id.SaranNitrit);

        txtidph = findViewById(R.id.TitlePH);
        txtnilaiph = findViewById(R.id.nilaiPH);
        txtkondisiph = findViewById(R.id.KondisiPH);
        txtsaranph = findViewById(R.id.SaranPH);

        txtidkecerahan = findViewById(R.id.TitleKecerahan);
        txtnilaikecerahan = findViewById(R.id.nilaiKecerahan);
        txtkondisikecerhan = findViewById(R.id.KondisiKecerahan);
        txtsarankecerahan = findViewById(R.id.SaranKecerahan);


        txtidsuhu = findViewById(R.id.TitleSuhu);
        txtnilaisuhu = findViewById(R.id.nilaiSuhu);
        txtkondisisuhu = findViewById(R.id.KondisiSuhu);
        txtsaransuhu = findViewById(R.id.SaranSuhu);

        txtidsalinitas = findViewById(R.id.TitleSalinitas);
        txtnilaisalinitas = findViewById(R.id.nilaiSalinitas);
        txtkondisisalinitas = findViewById(R.id.KondisiSalinitas);
        txtsaransalinitas = findViewById(R.id.SaranSalinitas);
        txtidsalinitas.setText("SALINITAS");
        txtiddo.setText("OKSIGEN TERLARUT");
        txtidamonia.setText("AMONIA");
        txtidnitrit.setText("NITRIT");
        txtidph.setText("PH");
        txtidkecerahan.setText("KECERAHAN");
        txtidsuhu.setText("SUHU");


        detaildo();
        detailamonia();
        detailkecerahan();
        detailnitrit();
        detailsalinitas();
        detailsuhu();
        detailph();
        setmonitoring();




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    private void setmonitoring(){


        // membuat req array dengan JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlmon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Register setmonitoring " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1)
                        Log.d("get edit data", jObj.toString());

                    setstartperiode.setText(jObj.getString("STARTPERIODE"));
                    setperiodeke.setText(jObj.getString("PERIODE"));


                    if(String.valueOf(jObj.getString("HASILPANEN")) == "null")
                    {
                        setendperiode.setText("Belum Berakhir");
                        sethasilpanen.setText("Belum Panen");

                    } else{
                        setendperiode.setText(jObj.getString("ENDPERIODE"));
                        sethasilpanen.setText(jObj.getString("HASILPANEN"));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi perubahan data adapter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(datailHasilmonitoring.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("idmon", idmonitoring);
                params.put("idlah", id_lahan);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(datailHasilmonitoring.this);
        requestQueue.add(stringRequest);
    }


    private void detaildo(){


        // membuat req array dengan JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urldo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Register Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

                        final String idkondisido = jObj.getString("IDKONDISIDO");

//
                        txtnilaido.setText(jObj.getString("NILAIDO"));
                        txtsarando.setText("(BATAS NORMAL oksigen >5)\n"+jObj.getString("SARANDO"));
                        txtkondisido.setText(jObj.getString("KONDISIDO"));



                    } else {
                        txtnilaido.setText("");
                        txtsarando.setText("");
                        txtkondisido.setText("");
//                        Toast.makeText(datailHasilmonitoring.this, jObj.getString(TAG_MESSAGE),
//                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi perubahan data adapter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(datailHasilmonitoring.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("id", idmonitoring);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(datailHasilmonitoring.this);
        requestQueue.add(stringRequest);
    }



    private void detailsalinitas(){


        // membuat req array dengan JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlsalinitas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Salinitas Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

//
                        final String idkondisisalinitas = jObj.getString("IDKONDISI_SALINITAS");
                        txtnilaisalinitas.setText(jObj.getString("NILAI_SALINITAS"));
                        txtsaransalinitas.setText("(BATAS NormaL SALINITAS 15 ppt– 25ppt)\n"+jObj.getString("SARAN_SALINITAS"));
                        txtkondisisalinitas.setText(jObj.getString("KONDISI_SALINITAS"));


                    } else {
                        txtnilaisalinitas.setText("");
                        txtsaransalinitas.setText("");
                        txtkondisisalinitas.setText("tidak ada inputan");
//                        Toast.makeText(datailHasilmonitoring.this, jObj.getString(TAG_MESSAGE),
//                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi perubahan data adapter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(datailHasilmonitoring.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("id", idmonitoring);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(datailHasilmonitoring.this);
        requestQueue.add(stringRequest);
    }


    private void detailkecerahan(){


        // membuat req array dengan JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlkecerahan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "KECERAHAN Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

                        final String idkondisi_kecerahan = jObj.getString("IDKONDISI_KECERAHAN");
                        txtnilaikecerahan.setText(jObj.getString("NILAI_KECERAHAN"));
                        txtsarankecerahan.setText("(BATAS NORMAL KECERAHAN 15cm – 30cm) \n" +jObj.getString("SARAN_KECERAHAN"));
                        txtkondisikecerhan.setText(jObj.getString("KONDISI_KECERAHAN"));

//

                    } else {
                        txtnilaikecerahan.setText("");
                        txtsarankecerahan.setText("");
                        txtkondisikecerhan.setText("tidak ada inputan");
//                        Toast.makeText(datailHasilmonitoring.this, jObj.getString(TAG_MESSAGE),
//                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi perubahan data adapter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(datailHasilmonitoring.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("id", idmonitoring);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(datailHasilmonitoring.this);
        requestQueue.add(stringRequest);
    }


    private void detailsuhu(){


        // membuat req array dengan JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlsuhu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "SUHU Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

                        final String idkondisi_suhu = jObj.getString("IDKONDISI_SUHU");
                        txtnilaisuhu.setText(jObj.getString("NILAI_SUHU"));
                        txtsaransuhu.setText("(BATAS NORMAL SUHU 28 – 30 (celcius))\n"+jObj.getString("SARAN_SUHU"));
                        txtkondisisuhu.setText(jObj.getString("KONDISI_SUHU"));


                    } else {
                        txtnilaisuhu.setText("");
                        txtsaransuhu.setText("");
                        txtkondisisuhu.setText("tidak ada inputan");
//                        Toast.makeText(datailHasilmonitoring.this, jObj.getString(TAG_MESSAGE),
//                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi perubahan data adapter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(datailHasilmonitoring.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("id", idmonitoring);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(datailHasilmonitoring.this);
        requestQueue.add(stringRequest);
    }



    private void detailph(){


        // membuat req array dengan JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlph, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "PH Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

                        final String idkondisi_ph = jObj.getString("IDKONDISI_PH");
                        txtnilaiph.setText(jObj.getString("NILAI_PH"));
                        txtsaranph.setText("(BATAS NORMAL  PH 6,5 - 7,5)\n"+jObj.getString("SARAN_PH"));
                        txtkondisiph.setText(jObj.getString("KONDISI_PH"));

                    } else {
                        txtnilaiph.setText("");
                        txtsaranph.setText("");
                        txtkondisiph.setText("tidak ada inputan");
//                        Toast.makeText(datailHasilmonitoring.this, jObj.getString(TAG_MESSAGE),
//                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi perubahan data adapter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(datailHasilmonitoring.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("id", idmonitoring);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(datailHasilmonitoring.this);
        requestQueue.add(stringRequest);
    }


    private void detailnitrit(){


        // membuat req array dengan JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlnitrit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Nitrit Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

                        final String idkondisi_nitrit = jObj.getString("IDKONDISI_NITRIT");
                        txtnilainitrit.setText(jObj.getString("NILAI_NITRIT"));
                        txtsarannitrit.setText("(BATAS NORMAL NITRIT <1)\n"+jObj.getString("SARAN_NITRIT"));
                        txtkondisinitrit.setText(jObj.getString("KONDISI_NITRIT"));

//

                    } else {
                        txtnilainitrit.setText("");
                        txtsarannitrit.setText("");
                        txtkondisinitrit.setText("tidak ada inputan");
//                        Toast.makeText(datailHasilmonitoring.this, jObj.getString(TAG_MESSAGE),
//                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi perubahan data adapter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(datailHasilmonitoring.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("id", idmonitoring);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(datailHasilmonitoring.this);
        requestQueue.add(stringRequest);
    }





    private void detailamonia(){


        // membuat req array dengan JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlamonia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Amonia Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

                        final String idkondisi_amonia = jObj.getString("IDKONDISI_AMONIA");
                        txtnilaiamonia.setText(jObj.getString("NILAI_AMONIA"));
                        txtsaranamonia.setText("(BATAS NORMAL amonia  <1)\n"+jObj.getString("SARAN_AMONIA"));
                        txtkondisiamonia.setText(jObj.getString("KONDISI_AMONIA"));

                    } else {
                        txtnilaiamonia.setText("");
                        txtsaranamonia.setText("");
                        txtkondisiamonia.setText("tidak ada inputan");
//                        Toast.makeText(datailHasilmonitoring.this, jObj.getString(TAG_MESSAGE),
//                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi perubahan data adapter
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(datailHasilmonitoring.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("id", idmonitoring);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(datailHasilmonitoring.this);
        requestQueue.add(stringRequest);
    }
}
