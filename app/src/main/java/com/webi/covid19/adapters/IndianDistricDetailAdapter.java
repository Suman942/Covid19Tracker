package com.webi.covid19.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webi.covid19.Model.DistrictModel;
import com.webi.covid19.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndianDistricDetailAdapter extends RecyclerView.Adapter<IndianDistricDetailAdapter.DistrictViewHolder> {

    Context mContext;
    ArrayList<DistrictModel> districtlist = new ArrayList<>();

    public IndianDistricDetailAdapter(Context mContext, ArrayList<DistrictModel> districtlist) {
        this.mContext = mContext;
        this.districtlist = districtlist;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.districtdetail,parent,false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
        holder.tvDistrict.setText(districtlist.get(position).district);
        holder.tvCases.setText(districtlist.get(position).confirmed);
        holder.tvRecovered.setText(districtlist.get(position).deceased);
        holder.tvDeaths.setText(districtlist.get(position).recovered);
        holder.tvActive.setText(districtlist.get(position).active);
    }

    @Override
    public int getItemCount() {
        return districtlist.size();
    }

    public class DistrictViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDistrict)
        TextView tvDistrict;
        @BindView(R.id.tvCases)
        TextView tvCases;
        @BindView(R.id.tvRecovered)
        TextView tvRecovered;
        @BindView(R.id.tvDeaths)
        TextView tvDeaths;
        @BindView(R.id.tvActive)
        TextView tvActive;

        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
