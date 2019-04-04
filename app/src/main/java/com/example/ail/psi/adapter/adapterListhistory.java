package com.example.ail.psi.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ail.psi.R;
import com.example.ail.psi.getset.listhistorylahangetset;

import java.util.List;

public class adapterListhistory extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<listhistorylahangetset> items;

    public adapterListhistory(Activity activity, List<listhistorylahangetset> items) {
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
            convertView = inflater.inflate(R.layout.listlahan_history, null);

        TextView nama = (TextView) convertView.findViewById(R.id.namahistory);
//        TextView isi = (TextView) convertView.findViewById(R.id.isi);
        listhistorylahangetset data = items.get(position);

        nama.setText(data.getNama());


        return convertView;
    }



}
