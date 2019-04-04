package com.example.ail.psi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ail.psi.app.AppController;
import com.example.ail.psi.getset.tampilMonitoringgetset;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class graphFragment extends Activity implements
        OnChartGestureListener, OnChartValueSelectedListener {

    private static final String TAG = "Graph";
    private String url = Server.URL + "graph.php";


    private String id_lahan;

    private LineChart mChart;

    private ArrayList<Entry> entries1, entries2;

    private LineChart mchart;
    String tag_json_obj = "json_obj_req";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragmen_graph);

        mchart = (LineChart) findViewById(R.id.linechart);
        mchart.setOnChartGestureListener(this);
        mchart.setOnChartValueSelectedListener(this);
        id_lahan = getIntent().getStringExtra("idl");

        //enable touch gestures
        mchart.setTouchEnabled(true);
        koneksi();


        // add data
//        getJSON();

        // get the legend (only possible after setting data)
//        Legend l = mChart.getLegend();
//
//        // modify the legend ...
//        // l.setPosition(LegendPosition.LEFT_OF_CHART);
//        l.setForm(Legend.LegendForm.LINE);

//

    }



    private void koneksi(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    JSONArray JArray = new JSONArray(response);
                    for (int i = 0; i < JArray.length(); i++) {
                        JSONObject answerInfo = JArray.getJSONObject(i);

                        if (answerInfo.length() == 1) {
                            entries2.add(new Entry(i + 1, 0));
                            entries2.add(new Entry(i + 2, Float.valueOf(answerInfo.getString("NILAI_AMONIA"))));
                        } else {
                            entries2.add(new Entry(i + 1, Float.valueOf(answerInfo.getString("NILAI_AMONIA"))));
                        }

                        //vote_count = new ArrayList<>();
                        //anslist = new ArrayList<>();
                        //vote_count.add(vote);
                        //anslist.add(answer);
                    }
                    LineDataSet dataSet = new LineDataSet(entries2, "DATA 2");
                    dataSet.setColor(Color.WHITE);
                    dataSet.setDrawFilled(true);
                    dataSet.setDrawCircles(false);
                    dataSet.setDrawValues(false);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setFillColor(000000);
                    dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                    LineData lineData = new LineData(dataSet);
                    mChart.setTouchEnabled(true);
                    mChart.setDrawGridBackground(false);
                    mChart.getDescription().setEnabled(false);

                    XAxis x = mChart.getXAxis();
                    x.setEnabled(false);
                    YAxis y = mChart.getAxisLeft();
                    y.setTextColor(Color.TRANSPARENT);
                    y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
                    y.setDrawGridLines(false);
                    y.setAxisLineColor(Color.TRANSPARENT);

                    mChart.getAxisRight().setEnabled(false);
                    mChart.getLegend().setEnabled(false);
                    mChart.animateXY(2000, 2000);

                    mChart.setData(lineData);
                    mChart.invalidate();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Didnt work. Catch. "+ response.toString(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Didnt work. Error : "+e.toString());

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.getMessage()==null){
                            Toast.makeText(getApplicationContext(), " ERROR ", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Error. Please check your internet connection. ErrorListener. " + error.getMessage(), Toast.LENGTH_LONG).show();


                        }

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idl", id_lahan);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }


        // TODO: adapter

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i(TAG, "onChartGestureStart: X:" + me.getX() + "Y:" + me.getY());
        Toast toast = Toast.makeText(this, "onChartGestureStart: X:" + me.getX() + "Y:" + me.getY(), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 20);
        toast.show();
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i(TAG, "onChartGestureEnd: " + lastPerformedGesture);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i(TAG, "onChartLongPressed: ");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i(TAG, "onChartDoubleTapped: ");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i(TAG, "onChartSingleTapped: ");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i(TAG, "onChartFling: veloX: " + velocityX + "veloY" + velocityY);
        Toast.makeText(this, "onChartFling: veloX: " + velocityX + "veloY" + velocityY, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i(TAG, "onChartScale: ScaleX: " + scaleX + "ScaleY: " + scaleY);
        Toast.makeText(this, "onChartScale: ScaleX: " + scaleX + "ScaleY: " + scaleY, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i(TAG, "onChartTranslate: dX" + dX + "dY" + dY);
        Toast.makeText(this, "onChartTranslate: dX" + dX + "dY" + dY, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i(TAG, "onValueSelected: " + e.toString());
        Toast.makeText(this, "onValueSelected: " + e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {
        Log.i(TAG, "onNothingSelected: ");
    }

}
