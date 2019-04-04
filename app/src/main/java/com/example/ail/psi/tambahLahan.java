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
import android.support.v4.widget.SwipeRefreshLayout;
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

public class tambahLahan extends Fragment {
    private ProgressDialog pDialog;
    ConnectivityManager conMgr;
    private ImageButton btntambahperiode;
    private EditText txtnamalahan;
    private EditText txtletaklahan;
    private Button btntambahlahan;
    private Context context;
    private int count;
    private TextView txtperiode;
    private RadioButton btnaktif;
    SwipeRefreshLayout swipe;
    int success;

    private static final String TAG = tambahLahan.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "param2";

    private String url = Server.URL + "tambahlahan.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tambahLahan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_entry.
     */
    // TODO: Rename and change types and number of parameters
    public static tambahLahan newInstance(String param1, String param2) {
        tambahLahan fragment = new tambahLahan();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //layout untuk fragment
        View v = inflater.inflate(R.layout.lahan_fragment, container, false);
        new myikc().execute();

        btntambahperiode = (ImageButton) v.findViewById(R.id.tambah);
        txtnamalahan = (EditText) v.findViewById(R.id.namalahan);
        txtletaklahan = (EditText) v.findViewById(R.id.letaklahan);
        btntambahlahan = (Button) v.findViewById(R.id.btntambahlahan);
        txtperiode = (TextView) v.findViewById(R.id.txtnilai);
        txtperiode.setText("1");
        return v;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class myikc extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            return null;
        }

        protected void onPostExecute(String file_url) {

            btntambahlahan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // do something


                    final String namalahan=txtnamalahan.getText().toString();
                    final String letaklahan=txtletaklahan.getText().toString();
                    final String priode=txtperiode.getText().toString();
                    final int status = ((RadioGroup) getView().findViewById(R.id.statuslahan)).getCheckedRadioButtonId();
                    final RadioButton rbstatus = ((RadioButton) getView().findViewById(status));
                    final String sdrbstatus = rbstatus.getText().toString();

                    if (namalahan.isEmpty() || namalahan.length() < 3){
                        txtnamalahan.setError("at least 3 characters");
                    }else if(letaklahan.trim().length() == 0 ) {
                        txtletaklahan.setError("Kolom Tidak Boleh Kosong");
                    }else {

                    //------volley

                    final String KEY_NAMA_LAHAN = "namalahan";
                    final String KEY_LETAK_LAHAN = "letaklahan";
                    final String KEY_PRIODE = "periode";
                    final String KEY_STATUS = "status";
                    final String KEY_IDLAHAN = "iduser";
                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setCancelable(false);
                    pDialog.setMessage("Wait ...");
                    pDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pDialog.dismiss();

                                    Log.e(TAG, "Register Response: " + response.toString());

                                    try {
                                        JSONObject jObj = new JSONObject(response);
                                        success = jObj.getInt(TAG_SUCCESS);

                                        // Check for error node in json
                                        if (success == 1) {

                                            Log.e("Successfully", jObj.toString());

                                            Toast.makeText(getActivity(),
                                                    jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                            txtnamalahan.setText("");
                                            txtletaklahan.setText("");
                                        } else {
                                            Toast.makeText(getActivity(),
                                                    jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                            }
                                         }
                                         catch (JSONException e) {
                                        // JSON error
                                        e.printStackTrace();
                                         }
                                //    Toast.makeText(getActivity(),"Sucsess", Toast.LENGTH_SHORT).show();
                                         }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    pDialog.dismiss();
                                    Toast.makeText(getActivity(),
                                            "No Internet Connection", Toast.LENGTH_LONG).show();
//                                    Toast.makeText(getActivity(),"error :" +error.toString() , Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put(KEY_NAMA_LAHAN,namalahan);
                            params.put(KEY_LETAK_LAHAN,letaklahan);
                            params.put(KEY_PRIODE,priode);
                            params.put(KEY_STATUS,sdrbstatus);
                            params.put(KEY_IDLAHAN, mParam1);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);
                    //-------------
                }}
            });
        }
    }
}