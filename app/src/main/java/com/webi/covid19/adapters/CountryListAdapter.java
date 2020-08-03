package com.webi.covid19.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.webi.covid19.Model.CountryMode;
import com.webi.covid19.R;

import java.util.List;

public class CountryListAdapter extends ArrayAdapter<CountryMode> {
    public  Context mContext;
    public List<CountryMode> countryList;


    public CountryListAdapter(Context context,List<CountryMode> countryList) {
        super(context, R.layout.custom_item_list,countryList);
        this.countryList = countryList;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_list,null,true);
        ImageView flagImg = view.findViewById(R.id.imgFlag);
        TextView countryName = view.findViewById(R.id.tvcountryName);

        countryName.setText(countryList.get(position).country);
        Glide.with(mContext).load(countryList.get(position).flag).into(flagImg);
        return view;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Nullable
    @Override
    public CountryMode getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}
