package com.webi.covid19.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.webi.covid19.Model.CountryMode;
import com.webi.covid19.R;
import com.webi.covid19.adapters.CountryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AffectedCountries extends AppCompatActivity {

    @BindView(R.id.listCountry)
    ListView listCountry;
    @BindView(R.id.loader)
    SimpleArcLoader simpleArcLoader;

    public static List<CountryMode> countryModelsList = new ArrayList<>();
    CountryMode countryModel;
    CountryListAdapter countryListAdapter;

    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);
        ButterKnife.bind(this);

        fetchData();
        listCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                startActivity(new Intent(AffectedCountries.this, DetailCountry.class).putExtra("position", position));
            }
        });
        // prepare interstitial ads
        interstitialAds();
    }

    private void interstitialAds() {
        // prepare interstitial ads
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-4687730962574001/4535636768");
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/countries";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String countryName = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todaycases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todaydeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");


                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flagUrl = object.getString("flag");

                        countryModel = new CountryMode(flagUrl, countryName, cases, todaycases, deaths, todaydeaths, recovered, active, critical);
                        countryModelsList.add(countryModel);
                    }

                    countryListAdapter = new CountryListAdapter(AffectedCountries.this, countryModelsList);
                    listCountry.setAdapter(countryListAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AffectedCountries.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        simpleArcLoader.stop();
                        simpleArcLoader.setVisibility(View.GONE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

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
