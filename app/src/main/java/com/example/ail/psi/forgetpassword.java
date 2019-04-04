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

public class forgetpassword  extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_register;
    ImageButton btn_login;
    EditText  txt_email;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "lupapassword.php";

    private static final String TAG = Register.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
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
        txt_email  = (EditText) findViewById(R.id.txt_email);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(forgetpassword.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String email = txt_email.getText().toString();
                String emailPattern ="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";



                if(email.isEmpty() || !email.matches(emailPattern)){
                    txt_email.setError("ex : example@gmail.com");

                }
                else
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    forgetpassword(email);
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

    private void forgetpassword(final String email) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "forgetpassword Response: " + response.toString());
                pDialog.dismiss();


                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Reset Password sukses!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                        txt_email.setText("");

                        intent = new Intent(forgetpassword.this, Login.class);
                        finish();
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        txt_email.setError("Email tidak ditemukan");
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
                Log.e(TAG, "forgetpassword Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
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
}
