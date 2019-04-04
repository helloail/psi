package com.example.ail.psi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Moh. Zinur Athhufail Addausi on 10/11/2018.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        lahan.OnFragmentInteractionListener,
        listLahan.OnFragmentInteractionListener,
        tambahLahan.OnFragmentInteractionListener{

    public static final String TAG_USERNAME = "username";
    public static final String TAG_ID = "id";
    TextView txt_username, txt_id;
    SharedPreferences sharedpreferences;
    String username, id_user;

    ConnectivityManager conMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        txt_username = (TextView) findViewById(R.id.textid);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView txt_username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textid);
        TextView txt_id = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtuser);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        id_user = getIntent().getStringExtra(TAG_ID);
        txt_username.setText(username);
        txt_id.setText(id_user);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
//                Snackbar.make(Context, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Toast.makeText (getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }



        Bundle bundle = new Bundle();
        bundle.putString("id", id_user);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new listlahanhistory();
        fragment.setArguments(bundle);

        tx.replace(R.id.framecontoh, fragment);
        tx.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,updateuser.class);
            intent.putExtra("id",id_user);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_monitoring) { //id pada layout activity_main_drawer.xml
            fragmentClass = listLahanMonitoring.class;
        } else if (id == R.id.nav_gallery) {
            fragmentClass = lahan.class;
        }else if (id == R.id.nav_slideshow){
            fragmentClass = listlahanhistory.class;
        }else if (id == R.id.nav_send) {
            logout();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Bundle bundle=new Bundle();
            bundle.putString("id",id_user);
            bundle.putString("username",username);
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (id != R.id.nav_send){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framecontoh, fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout () {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(Login.session_status, false);
                        editor.putString(TAG_USERNAME, null);
                        editor.putString(TAG_ID, null);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
        @Override
    public void onFragmentInteraction(Uri uri) {
    }
}