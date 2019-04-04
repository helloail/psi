package com.example.ail.psi.adapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ail.psi.R;
import com.example.ail.psi.getset.listmonitorlahangetset;

import java.util.List;

/**
 * Created by Philipus on 26/07/2016.
 */
public class adapterListmonitoring extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<listmonitorlahangetset> items;

    public adapterListmonitoring(Activity activity, List<listmonitorlahangetset> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.listlahan_monitoring, null);

        TextView nama = (TextView) convertView.findViewById(R.id.namamonitor);
        TextView lastcek = (TextView) convertView.findViewById(R.id.lastceklahan);

        listmonitorlahangetset data = items.get(position);

        nama.setText(data.getNama());
        lastcek.setText("Last Cek :" + data.getLastcek());


        return convertView;
    }

}