package com.example.dn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class Main3Activity extends AppCompatActivity {

    ListView listView;
    ArrayList<history> historyArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        listView = findViewById(R.id.Lvhistory);
        historyArrayAdapter = new ArrayList<>();
        DBManager dbManager = new DBManager(this);
        historyArrayAdapter = dbManager.getAllStudent();
        Custom_history customAdapter = new Custom_history(this, R.layout.item_lisview, historyArrayAdapter);
        listView.setAdapter(customAdapter);

    }
}
