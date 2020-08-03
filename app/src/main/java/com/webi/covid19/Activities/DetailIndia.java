package com.webi.covid19.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.webi.covid19.Model.DistrictModel;
import com.webi.covid19.R;
import com.webi.covid19.adapters.IndianDistricDetailAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailIndia extends AppCompatActivity {
    @BindView(R.id.recyclerrView)
    RecyclerView recyclerView;
    int position;
    String url = "https://api.covid19india.org/v2/state_district_wise.json";
    IndianDistricDetailAdapter indianDistricDetailAdapter;
    ArrayList<DistrictModel> districtList = new ArrayList<>();
    Intent intent;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_india);
        ButterKnife.bind(this);
        intent = getIntent();
        position = intent.getIntExtra("position", 0);
        fetchdata();
        // prepare interstitial ads
        interstitialAds();
    }

    private void interstitialAds() {
        // prepare interstitial ads
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-4687730962574001/4535636768");
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void fetchdata() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray jsonArray = jsonObject.getJSONArray("districtData");
                        if (position == i) {
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                String district = jsonObject1.getString("district");
                                String cases = jsonObject1.getString("confirmed");
                                String recovered = jsonObject1.getString("recovered");
                                String deaths = jsonObject1.getString("deceased");
                                String active = jsonObject1.getString("active");

                                districtList.add(new DistrictModel(district, cases, recovered, deaths,active));

                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DetailIndia.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                indianDistricDetailAdapter = new IndianDistricDetailAdapter(getApplicationContext(), districtList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(indianDistricDetailAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailIndia.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
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
