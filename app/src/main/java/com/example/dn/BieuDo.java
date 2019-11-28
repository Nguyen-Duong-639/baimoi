package com.example.dn;

import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class BieuDo implements OnChartValueSelectedListener {
    private PieChart mChart;

    @Override
    public void onValueSelected(Entry e, Highlight h) {


    }

    @Override
    public void onNothingSelected() {

    }
}
