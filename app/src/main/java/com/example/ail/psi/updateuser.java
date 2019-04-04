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
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.RadioButton;
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

public class updateuser  extends AppCompatActivity {

    ProgressDialog pDialog;
    Toolbar toolbar;
    Button btn_register;
    ImageButton btn_login;
    EditText txt_username;
    EditText txt_password;
    EditText txt_confirm_password;
    EditText txt_alamatuser;
    EditText txt_email;
    EditText txt_hp;
    EditText txt_newpass;
    Intent intent;
    String iduser;
    String tempPass;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "updateuser.php";
    private String urledit = Server.URL + "edituser.php";


    private static final String TAG = Register.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateuser);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iduser = getIntent().getStringExtra("id");
        setTitle(iduser);

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
        edit();


        btn_register = (Button) findViewById(R.id.bt_register);
        txt_username = (EditText) findViewById(R.id.t_username);
        txt_alamatuser= (EditText) findViewById(R.id.t_alamatuser);
        txt_email  = (EditText) findViewById(R.id.t_email);
        txt_hp = (EditText) findViewById(R.id.t_hp);
        txt_newpass = (EditText) findViewById(R.id.t_newpass);
        txt_password = (EditText) findViewById(R.id.t_password);
        txt_confirm_password = (EditText) findViewById(R.id.t_confirm_password);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String passold = txt_password.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();
                String alamatuser = txt_alamatuser.getText().toString();
                String email = txt_email.getText().toString();
                String newpass = txt_newpass.getText().toString();
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
                }else if(!passold.equals(tempPass)){
                    txt_password.setError("Password Tidak di Temukan");
                    kosongteks();
                }else if(newpass.isEmpty()|| newpass.length() < 6){
                    txt_newpass.setError("at least 6 characters");
                    kosongteks();
                }else if(confirm_password.isEmpty()){
                    txt_confirm_password.setError("password tidak sama");
                    kosongteks();
                }else
                if(!newpass.equals(confirm_password)) {
                    txt_confirm_password.setError("password tidak sama");
                } else
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    update(username, alamatuser, email, noHp, newpass, confirm_password);
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

    private void update(final String username, final String alamatuser, final String email, final String noHp, final String newpass, final String confirm_password) {
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
                         onBackPressed();

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
                params.put("idpem", iduser);
                params.put("username", username);
                params.put("alamatuser", alamatuser);
                params.put("email", email);
                params.put("hp", noHp);
                params.put("password", newpass);

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


    private void edit (){

        StringRequest strReq = new StringRequest(Request.Method.POST, urledit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String id = jObj.getString("ID_PEMBUDIDAYA");

                        txt_username.setText(jObj.getString("USER_NAME"));
                        txt_alamatuser.setText(jObj.getString("ALAMAT_PEMBUDIDAYA"));
                        txt_hp.setText(jObj.getString("NOHP"));
                        txt_email.setText(jObj.getString("EMAIL"));
                        txt_password.setText(jObj.getString("PASSWORD"));
                        tempPass = jObj.getString("PASSWORD");


//

                    } else {
                        Toast.makeText(updateuser.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(updateuser.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post webservice
                Map<String, String> params = new HashMap<String, String>();
                params.put("idpem", iduser);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }



    public void kosongteks(){
        txt_password.setText("");
        txt_confirm_password.setText("");
    }

}