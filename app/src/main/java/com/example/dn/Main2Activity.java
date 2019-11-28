package com.example.dn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap gm;
    private  double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent=getIntent();
        latitude=intent.getDoubleExtra("kd",0);
        longitude=intent.getDoubleExtra("vd",0);
    }
    private void createMap() {
        SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gMap);
        smf.getMapAsync(Main2Activity.this);// lấy bản đồ không đồng bộ
    }
    @Override
    protected void onResume() {
        super.onResume();
        createMap();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng hanoi = new LatLng(latitude, longitude);
        gm = googleMap;
        gm.addMarker(new MarkerOptions().position(hanoi).title("Hà Nội"));
        CameraPosition cp = new CameraPosition.Builder().target(hanoi).zoom(15).build();
        gm.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }
}
