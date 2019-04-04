package com.example.ail.psi.adapter;


        import android.app.Activity;
        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.example.ail.psi.R;


        import com.example.ail.psi.getset.tampilMonitoringgetset;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;


/**
 * Created by Philipus on 26/07/2016.
 */
public class adaptertampilmonitoring extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<tampilMonitoringgetset> items;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public adaptertampilmonitoring(Activity activity, List<tampilMonitoringgetset> items) {
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
    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.listtampil_monitoring, null, false);

        TextView datee = (TextView) convertView.findViewById(R.id.tanggalmonitor);
        TextView id = (TextView) convertView.findViewById(R.id.idmonitor);

        tampilMonitoringgetset data = items.get(position);
        Date date = new Date();

//            datee.setText(String.valueOf(data.getTanggal()));
            id.setText(data.getId());

        return convertView;
    }

}