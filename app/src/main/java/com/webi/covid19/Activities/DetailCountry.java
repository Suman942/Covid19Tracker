package com.webi.covid19.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.webi.covid19.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailCountry extends AppCompatActivity {

    int position;
    @BindView(R.id.tvCases)
    TextView tvCases;
    @BindView(R.id.tvActive)
    TextView tvActive;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.tvRecovered)
    TextView tvRecovered;
    @BindView(R.id.tvTodayCases)
    TextView tvTodayCases;
    @BindView(R.id.tvTodayDeaths)
    TextView tvTodayDeaths;

    @BindView(R.id.piechart)
    PieChart pieChart;
    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_country);
        ButterKnife.bind(this);
        Intent intent =getIntent();
        position = intent.getIntExtra("position",0);
        tvCases.setText(AffectedCountries.countryModelsList.get(position).cases);
        tvRecovered.setText(AffectedCountries.countryModelsList.get(position).recovered);
        tvActive.setText(AffectedCountries.countryModelsList.get(position).active);
        tvTodayCases.setText(AffectedCountries.countryModelsList.get(position).todayCases);
        tvTodayDeaths.setText(AffectedCountries.countryModelsList.get(position).todayDeaths);
        tvCountry.setText(AffectedCountries.countryModelsList.get(position).country);

        pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFEB3B")));
        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#1B5E20")));
        pieChart.addPieSlice(new PieModel("Active Cases", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#311B92")));
        pieChart.startAnimation();
        // prepare interstitial ads
        interstitialAds();
    }
    private void interstitialAds() {
        // prepare interstitial ads
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-4687730962574001/4535636768");
        interstitialAd.loadAd(new AdRequest.Builder().build());
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
