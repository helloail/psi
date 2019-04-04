package com.example.ail.psi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ail.psi.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_register;
    ImageButton btn_login;
    EditText txt_username, txt_password, txt_confirm_password, txt_alamatuser, txt_email, txt_hp;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "register.php";

    private static final String TAG = Register.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTitle("Register");

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_login = (ImageButton) findViewById(R.id.back);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_alamatuser= (EditText) findViewById(R.id.txt_alamatuser);
        txt_email  = (EditText) findViewById(R.id.txt_email);
        txt_hp = (EditText) findViewById(R.id.txt_hp);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirm_password = (EditText) findViewById(R.id.txt_confirm_password);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(Register.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();
                String alamatuser = txt_alamatuser.getText().toString();
                String email = txt_email.getText().toString();
                String noHp = txt_hp.getText().toString();
                String emailPattern ="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


                if (username.isEmpty() || username.length() < 3){
                    txt_username.setError("at least 3 characters");
                    kosongteks();
                }else if(alamatuser.trim().length() == 0 ) {
                    txt_alamatuser.setError("Kolom Tidak Boleh Kosong");
                    kosongteks();
                } else if(email.isEmpty() || !email.matches(emailPattern)){
                    txt_email.setError("ex : example@gmail.com");
                    kosongteks();
                }else if(noHp.length()< 11 || noHp.length()>12){
                    txt_hp.setError("ex: 085123456789");
                    kosongteks();
                }else if(password.isEmpty()|| password.length() < 6){
                    txt_password.setError("at least 6 characters");
                    kosongteks();
                }else if(confirm_password.isEmpty()){
                    txt_confirm_password.setError("password tidak sama");
                    kosongteks();
                }else
                if(!password.equals(confirm_password)) {
                    txt_confirm_password.setError("password tidak sama");
                } else
                    if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkRegister(username, alamatuser, email, noHp, password, confirm_password);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void checkRegister(final String username, final String alamatuser, final String email, final String noHp, final String password, final String confirm_password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                pDialog.dismiss();


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        txt_username.setText("");
                        txt_alamatuser.setText("");
                        txt_email.setText("");
                        txt_hp.setText("");
                        txt_password.setText("");
                        txt_confirm_password.setText("");
                        intent = new Intent(Register.this, Login.class);
                        finish();
                        startActivity(intent);

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
                Log.e(TAG, "Login Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("alamatuser", alamatuser);
                params.put("email", email);
                params.put("hp", noHp);
                params.put("password", password);
                params.put("confirm_password", confirm_password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 1000);
    }

    public void kosongteks(){
        txt_password.setText("");
        txt_confirm_password.setText("");
    }

}