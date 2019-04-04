package com.example.ail.psi;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.Fragment;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ListView;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.VolleyLog;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.ail.psi.adapter.adaptertampilmonitoring;
        import com.example.ail.psi.app.AppController;
        import com.example.ail.psi.getset.tampilMonitoringgetset;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class listmonitoring extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    SwipeRefreshLayout swipe;
    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    adaptertampilmonitoring adapter;
    public static final String TAG_IDl = "idl";

    private static final String TAG = com.example.ail.psi.listmonitoring.class.getSimpleName();

    private String url = Server.URL +"listtampilmonitoring.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "username";
    private String mParam1;
    private String mParam2;
    String tag_json_obj = "json_obj_req";
    String id_lahan;



    List<tampilMonitoringgetset> itemList = new ArrayList<tampilMonitoringgetset>();

    public listmonitoring() {
        // Required empty public constructor
    }


    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_tampilmonitoring);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        id_lahan = getIntent().getStringExtra(TAG_IDl);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        fab     = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_tampil_history);
        list    = (ListView) findViewById(R.id.listtampilmonitoring);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(listmonitoring.this,graphFragment.class);
//                intent.putExtra("idmon",idmon);
//                intent.putExtra("tanggalmon",tanggalmon);
                intent.putExtra("idl",id_lahan);
                startActivity(intent);
            }
        });


            adapter = new adaptertampilmonitoring(listmonitoring.this, itemList);
            list.setAdapter(adapter);

            setTitle("Waktu Monitoring");


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
                final String idmon = itemList.get(position).getTanggal();
                final String tanggalmon = itemList.get(position).getId();

                Intent intent = new Intent(listmonitoring.this,datailHasilmonitoring.class);
                intent.putExtra("idmon",idmon);
                intent.putExtra("tanggalmon",tanggalmon);
                intent.putExtra("idl",id_lahan);
                startActivity(intent);

            }});

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

                        tampilMonitoringgetset item = new tampilMonitoringgetset();
                        item.setTanggal(obj.getString("ID_MONITORING"));
                        item.setID(obj.getString("TANGGAL_MONITORING"));


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
                params.put("idl", id_lahan);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(listmonitoring.this);
        requestQueue.add(stringRequest);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
