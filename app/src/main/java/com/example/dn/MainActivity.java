package com.example.dn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import com.github.mikephil.charting.charts.CombinedChart;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.CombinedData;
//import com.github.mikephil.charting.data.DataSet;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.github.mikephil.charting.formatter.ValueFormatter;
//import com.github.mikephil.charting.highlight.Highlight;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        OnChartValueSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    RecyclerView theoGio;
    RecyclerView theongay;

    ImageView findcity, here, maps, history63;
    TextView txtName, txtndmax, txtndmin, txtStatus, txtHumidity, txtCloud, txtWind, txtDay, txtNgay;
    ImageView imageIcon;
    EditText etfindcity;
    LinearLayout all;
    ImageView image;
    TextView statusitem, ndmax, ndmin, day;
    private CombinedChart mChart;
    static ArrayList<Contact> arrayList = new ArrayList<Contact>();

//    Tương tác với google api
    private Location location;
    private GoogleApiClient gac;
//    Hiển thị vị trí
    private double latitude = 0;
    private double longitude = 0;

    private history history6;
    DBManager dbManager = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChart = findViewById(R.id.linechart);
        findcity = findViewById(R.id.findcity);
        theongay = findViewById(R.id.theongay);
        txtName = findViewById(R.id.cityShow);
        here = findViewById(R.id.here);
        history63=findViewById(R.id.history);
        etfindcity = findViewById(R.id.etfindcity);
        maps = findViewById(R.id.maps);

        txtndmax = findViewById(R.id.celsiusmax);
        txtndmin = findViewById(R.id.celsiusmin);
        txtStatus = findViewById(R.id.weather);
        txtHumidity = findViewById(R.id.doAm);
        txtCloud = findViewById(R.id.pcloud);
        txtWind = findViewById(R.id.tocdogio);
        txtDay = findViewById(R.id.thu);
        txtNgay=findViewById(R.id.ngay);
        imageIcon = findViewById(R.id.imagewerther);

        day = findViewById(R.id.thu);
        image = findViewById(R.id.image);
        statusitem = findViewById(R.id.statusitem);
        ndmax = findViewById(R.id.ndmax);
        ndmin = findViewById(R.id.ndmin);

        all = findViewById(R.id.all);

        setFindcity();
        ttinChiTiet();
        String data = etfindcity.getText().toString();
        if (data.equals("")) {
            GetWeatherData("hanoi");
            weather7("hanoi");
            weatherDay("hanoi");
            bieuDo("hanoi");
        } else {
//            GetWeatherData(data);
            weather7(data);
            weatherDay(data);
            bieuDo(data);}

//      phần này để gọi yêu cầu truy cập đt để lấy vị trí hiện tại của đt ===========
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }//======================= gọi truy cập
        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();    // lấy vị trí hiện tại
            }
        });
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPlayServices()) {
                    // Building the GoogleApi client
                    buildGoogleApiClient();}
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                getLocation();
                intent.putExtra("kd", latitude);
                intent.putExtra("vd", longitude);
                startActivity(intent);
            }
        });
        history63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Main3Activity.class);
                startActivity(intent);
            }
        });
    }
//       ==============================================
//    lấy vị trí hiện tại
    public void dispLocation(View view) {
        getLocation();
    }
    /**
     * Phương thức này dùng để hiển thị trên UI
     */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Kiểm tra quyền hạn
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            location = LocationServices.FusedLocationApi.getLastLocation(gac);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                // Hiển thị

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=be8d3e323de722ff78208a7dbb2dcd6f";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                Log.d("ket qua", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("name");
                            GetWeatherData(data);
                            weather7(data);
                            weatherDay(data);
                            bieuDo(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                requestQueue.add(stringRequest);

//                tvLocation.setText(latitude + ", " + longitude);
            } else {
                Toast.makeText(getBaseContext(), "Bật chia sẻ vị trí để hiển thị thông tin", Toast.LENGTH_LONG).show();
//                tvLocation.setText("(Không thể hiển thị vị trí. " +
//                        "Bạn đã kích hoạt location trên thiết bị chưa?)");
            }
        }
    }
    /**
     * Tạo đối tượng google api client
     */
    protected synchronized void buildGoogleApiClient() {
        if (gac == null) {
            gac = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }
    }
    /**
     * Phương thức kiểm chứng google play services trên thiết bị
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1000).show();
            } else {
                Toast.makeText(this, "Thiết bị này không hỗ trợ.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Đã kết nối với google api, lấy vị trí
        getLocation();
    }
    @Override
    public void onConnectionSuspended(int i) {
        gac.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
    protected void onStart() {
          gac.connect();
        super.onStart();
    }
    protected void onStop() {
        gac.disconnect();
        super.onStop();
    }
//    hết lấy vi trí hiện tại
//    ============================================================

//    phần vẽ biểu đồ nhiệt độ
    public void bieuDo(String whe) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + whe + "&cnt=8&units=metric&appid=be8d3e323de722ff78208a7dbb2dcd6f";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArrayList.length(); i++) {
                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                        String ngay = jsonObjectList.getString("dt");
                        long l = Long.valueOf(ngay);
                        Date date = new Date(l * 1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                        String Day = simpleDateFormat.format(date);

                        JSONObject main = jsonObjectList.getJSONObject("temp");
                        String temp = main.getString("day");
                        Double a = Double.valueOf(temp);
                        String Nhietdomax = String.valueOf(a.intValue()); // a.intValue() chuyen  a thanh kieu so int    .. String.valueOf chuyen thanh kieu chuoi

                        arrayList.add(new Contact(Day, Nhietdomax));
                    }
                    mChart = (CombinedChart) findViewById(R.id.linechart);
                    mChart.getDescription().setEnabled(false);
                    mChart.setBackgroundColor(Color.WHITE);
                    mChart.setDrawGridBackground(false);
//        mChart.setDrawBarShadow(false);
//        mChart.setHighlightFullBarEnabled(false);
                    mChart.setOnChartValueSelectedListener(MainActivity.this);

                    YAxis rightAxis = mChart.getAxisRight();
                    rightAxis.setDrawGridLines(false);
                    rightAxis.setAxisMinimum(0f);

                    YAxis leftAxis = mChart.getAxisLeft();
                    leftAxis.setDrawGridLines(false);
                    leftAxis.setAxisMinimum(0f);

                    final List<String> xLabel = new ArrayList<>();
                    xLabel.add(arrayList.get(0).getNgay());
                    xLabel.add(arrayList.get(1).getNgay());
                    xLabel.add(arrayList.get(2).getNgay());
                    xLabel.add(arrayList.get(3).getNgay());
                    xLabel.add(arrayList.get(4).getNgay());
                    xLabel.add(arrayList.get(5).getNgay());
                    xLabel.add(arrayList.get(6).getNgay());
//                    xLabel.add(arrayList.get(7).getNgay());
                    XAxis xAxis = mChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setAxisMinimum(0f);
                    xAxis.setGranularity(1f);
                    xAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return xLabel.get((int) value % xLabel.size());
                        }
                    });

                    CombinedData data = new CombinedData();
                    LineData lineDatas = new LineData();
                    lineDatas.addDataSet((ILineDataSet) dataChart());

                    data.setData(lineDatas);

                    xAxis.setAxisMaximum(data.getXMax() + 0.25f);

                    mChart.setData(data);
                    mChart.invalidate();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }

    @Override
    public void onNothingSelected() {

    }

    private static DataSet dataChart() {

        LineData d = new LineData();
        int[] data = new int[]{1, 2, 2, 1, 1, 1, 2};
        for (int i = 0; i < data.length; i++) {
            data[i] = Integer.parseInt(arrayList.get(i).getMua());
        }
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 7; index++) {
            entries.add(new Entry(index, data[index]));
//            entries.add(new Entry(index,data ));
        }

        LineDataSet set = new LineDataSet(entries, "Lượng mưa các ngày");
        set.setColor(Color.BLUE);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.BLUE);
        set.setCircleRadius(2f);
        set.setFillColor(Color.BLUE);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.BLUE);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);
        return set;
    }
//===============================================================
//    thời tiết theo ngày
    private void weatherDay(final String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + data + "&cnt=7&units=metric&appid=be8d3e323de722ff78208a7dbb2dcd6f";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        theongay = findViewById(R.id.theongay);
        theongay.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        theongay.setLayoutManager(layoutManager);
        final ArrayList<Custom_ngay> arrayList = new ArrayList<Custom_ngay>();
        final Adapter_ngay adapterNgay = new Adapter_ngay(arrayList, MainActivity.this);
        theongay.setAdapter(adapterNgay);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("ketqua", "json: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                                String doam = jsonObjectList.getString("humidity");
                                String ngay = jsonObjectList.getString("dt");
                                long l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                                String Day = simpleDateFormat.format(date);

                                JSONObject main = jsonObjectList.getJSONObject("temp");

                                String max = main.getString("max");
                                Double a = Double.valueOf(max);
                                String Ndmax = String.valueOf(a.intValue()); // a.intValue() chuyen  a thanh kieu so int    .. String.valueOf chuyen thanh kieu chuoi

                                String min = main.getString("min");
                                Double b = Double.valueOf(min);
                                String Ndmin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                arrayList.add(new Custom_ngay(Day, doam, Ndmax, Ndmin));
                            }
                            adapterNgay.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
//    =====================================================================================
//    thời tiết theo giờ

    private void weather7(String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=8&appid=b15c6285f8ad4ea604deee730d0fde7f";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        theoGio = findViewById(R.id.theogio);
        theoGio.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        theoGio.setLayoutManager(layoutManager);
        final ArrayList<custom_gio> arrayList = new ArrayList<custom_gio>();
        final adapter_gio adapterGio = new adapter_gio(arrayList, MainActivity.this);
        theoGio.setAdapter(adapterGio);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                                String ngay = jsonObjectList.getString("dt");
                                long l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                String Day = simpleDateFormat.format(date);

                                JSONObject main = jsonObjectList.getJSONObject("main");
                                String doam = main.getString("humidity");

                                String max = main.getString("temp");
                                Double a = Double.valueOf(max);
                                String Nhietdomax = String.valueOf(a.intValue()); // a.intValue() chuyen  a thanh kieu so int    .. String.valueOf chuyen thanh kieu chuoi

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                arrayList.add(new custom_gio(Day, doam + "%", Nhietdomax));
                            }
                            adapterGio.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
//=============================================================================
//    thời tiết(vòng tròn) theo tìm kiếm
    public void GetWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=b15c6285f8ad4ea604deee730d0fde7f";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("ket qua", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String day = jsonObject.getString("dt");
                    String name = jsonObject.getString("name");
                    txtName.setText(name);

                    long l = Long.valueOf(day);
                    Date date = new Date(l * 1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                    String Day = simpleDateFormat.format(date);
                    txtDay.setText(Day);
                    SimpleDateFormat ngay = new SimpleDateFormat("dd-MM-yyyy");
                    String Ngay = ngay.format(date);
                    txtNgay.setText(Ngay);

                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                    String status = jsonObjectWeather.getString("main");
                    txtStatus.setText(status);

                    String icon = jsonObjectWeather.getString("icon");
                    Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + icon + ".png").into(imageIcon);

                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");

                    String nhietdomax = jsonObjectMain.getString("temp_max");
                    String nhietdomin = jsonObjectMain.getString("temp_min");
                    Double a = Double.valueOf(nhietdomax);
                    Double b = Double.valueOf(nhietdomin);
                    String Nhietdomax = String.valueOf(a.intValue());
                    String Nhietdomin = String.valueOf(b.intValue());
                    txtndmax.setText(Nhietdomax);
                    txtndmin.setText(Nhietdomin);

                    String doam = jsonObjectMain.getString("humidity");
                    txtHumidity.setText(doam + "%");

                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    txtWind.setText(gio + " m/s");

                    JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectClouds.getString("all");
                    txtCloud.setText(may + " %");

                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");

                    String nameSys = jsonObjectSys.getString("name");

                    txtName.setText(nameSys);

                    history6 = new history(name, Day, a, status, Ngay);
                    dbManager.addHistory1(history6);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
//    ===============================================================================
//    tìm kiếm
    public void setFindcity() {
        findcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String city = etfindcity.getText().toString();
                etfindcity.setVisibility(EditText.VISIBLE);
                GetWeatherData(city);
                weather7(city);
                weatherDay(city);
                bieuDo(city);
                all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etfindcity.setVisibility(EditText.GONE);
                        etfindcity.setText("");
                    }
                });
            }
        });
    }
//      ========================
//    chú thích của button maps,here.
    public void ttinChiTiet() {
        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getBaseContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.herect,
                        popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.herect:
                                Toast.makeText(getBaseContext(), "Vị trí hiện tại", Toast.LENGTH_SHORT).show();
                                break;

                        }
                        return false;
                    }
                });
//                getWindow().setLayout(8,6);
                popupMenu.show();
            }

        });
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getBaseContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.bando,
                        popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.bando:
                                Toast.makeText(getBaseContext(), "Tìm kiếm trên bản đồ", Toast.LENGTH_SHORT).show();
                                break;

                        }
                        return false;
                    }
                });
//                getWindow().setLayout(8,6);
                popupMenu.show();
            }

        });
    }
//    =====
}