package com.webi.covid19.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.leo.simplearcloader.SimpleArcLoader;
import com.webi.covid19.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndiaTotalStats extends AppCompatActivity {
    @BindView(R.id.tvCases)
    TextView tvCases;
    @BindView(R.id.tvActive)
    TextView tvActive;
    @BindView(R.id.tvRecovered)
    TextView tvRecovered;
    @BindView(R.id.tvTodayCases)
    TextView tvTodayCases;
    @BindView(R.id.tvTodayDeaths)
    TextView tvTodayDeaths;
    @BindView(R.id.tvTotalDeaths)
    TextView tvTotalDeaths;
    @BindView(R.id.loader)
    SimpleArcLoader simpleArcLoader;
    @BindView(R.id.scrollstats)
    ScrollView scrollView;
    @BindView(R.id.piechart)
    PieChart pieChart;

    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india_total_stats);
        ButterKnife.bind(this);

        fetchData();
        // prepare interstitial ads
        interstitialAds();
    }

    private void interstitialAds() {
        // prepare interstitial ads
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-4687730962574001/4535636768");
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
    private void fetchData()
    {
        String url = "https://corona.lmao.ninja/v2/countries/india";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tvCases.setText(jsonObject.getString("cases"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));

                    pieChart.addPieSlice(new PieModel("cases", Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFEB3B")));
                    pieChart.addPieSlice(new PieModel("recovered", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#1B5E20")));
                    pieChart.addPieSlice(new PieModel("active", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#311B92")));
                    pieChart.addPieSlice(new PieModel("deaths", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#B71C1C")));

                    pieChart.startAnimation();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IndiaTotalStats.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        simpleArcLoader.stop();
                        simpleArcLoader.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @OnClick(R.id.trackBtn) void trackCountries()
    {
        startActivity(new Intent(IndiaTotalStats.this, IndiaActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            finish();
        } else {
            interstitialAd.show();
            finish();
        }
    }
}
