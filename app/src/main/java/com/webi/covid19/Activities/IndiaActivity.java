package com.webi.covid19.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.leo.simplearcloader.SimpleArcLoader;
import com.webi.covid19.Model.IndiaStateDistrictModel;
import com.webi.covid19.R;
import com.webi.covid19.adapters.IndiaStateAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndiaActivity extends AppCompatActivity implements IndiaStateAdapter.Callback {

    @BindView(R.id.loader)
    SimpleArcLoader simpleArcLoader;
    @BindView(R.id.recyclerViewstates)
    RecyclerView recyclerViewstates;
    InterstitialAd interstitialAd;

    public static List<IndiaStateDistrictModel> indiaStateDistrictModelList = new ArrayList<>();
    IndiaStateDistrictModel indiaStateDistrictModel;
    IndiaStateAdapter indiaStateAdapter;
    String url = "https://api.covid19india.org/v2/state_district_wise.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india);
        ButterKnife.bind(this);
        fetchData();
        recyclerViewstates.setLayoutManager(new LinearLayoutManager(this));
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

        simpleArcLoader.start();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String state = jsonObject.getString("state");


//                        JSONArray jsonArray = jsonObject.getJSONArray("districtData");
//                        for (int j = 0; j < jsonArray.length(); j++)
//                        {
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(j);
//
//                             district = jsonObject1.getString("active");
//
//                            Log.e("LogMsg",""+district);
//
//                           // indiaStateDistrictModelList.add(new IndiaStateDistrictModel(state,district));
//
//                        }


                        indiaStateDistrictModelList.add(new IndiaStateDistrictModel(state));


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(IndiaActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                indiaStateAdapter = new IndiaStateAdapter(getApplicationContext(), indiaStateDistrictModelList, IndiaActivity.this);
                recyclerViewstates.setAdapter(indiaStateAdapter);


                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IndiaActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }


    @OnClick(R.id.trackBtn)
    void track() {
        startActivity(new Intent(IndiaActivity.this, AffectedCountries.class));
    }

    @Override
    public void detailReport(int position) {
        simpleArcLoader.start();
        startActivity(new Intent(IndiaActivity.this, DetailIndia.class).putExtra("position", position));
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
