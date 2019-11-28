package com.example.dn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Custom_history extends ArrayAdapter<history> {

    DBManager dbManager=new DBManager(getContext());
    private Context context;
    private ArrayList<history> histories;
    private int resource;

    public Custom_history(@NonNull Context context, int resource, @NonNull ArrayList<history> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.histories = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_lisview, parent, false);
        TextView Tvtimehistory = convertView.findViewById(R.id.Tvtimehistory);
        TextView Tvhistoryname = convertView.findViewById(R.id.Tvhistoryname);
        TextView Tvhistorynhietdo = convertView.findViewById(R.id.Tvhistorynhietdo);
        TextView Tvtrangthaitt = convertView.findViewById(R.id.Tvtrangthaitt);
        TextView Tvdayandmonth = convertView.findViewById(R.id.Tvdayandmonth);
        history history1 = histories.get(position);

        Tvhistoryname.setText(history.getName());
        Tvhistorynhietdo.setText(history1.getTemperature() + "");
        Tvtimehistory.setText(history1.getTime());
        Tvdayandmonth.setText(history1.getDayandmonth());
        Tvtrangthaitt.setText(history1.getTrangthaitt());


        return convertView;
    }
}
