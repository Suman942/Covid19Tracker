package com.webi.covid19.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.webi.covid19.Model.IndiaStateDistrictModel;
import com.webi.covid19.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndiaStateAdapter extends RecyclerView.Adapter<IndiaStateAdapter.IndiaDetailViewHolder> {
    public interface Callback {
        void detailReport(int position);
    }

    Callback mCallback;
    public Context mContext;
    public List<IndiaStateDistrictModel> indiaStateDistrictModelList;

    public IndiaStateAdapter(Context mContext,  List<IndiaStateDistrictModel> indiaStateDistrictModelList, Callback mCallback) {
        this.mContext = mContext;
        this.indiaStateDistrictModelList = indiaStateDistrictModelList;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public IndiaDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.state_list_layout, parent, false);
        return new IndiaDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndiaDetailViewHolder holder, int position) {
        holder.tvData.setText(indiaStateDistrictModelList.get(position).state);
        holder.statelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.detailReport(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return indiaStateDistrictModelList.size();
    }

    public class IndiaDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvdata)
        TextView tvData;
        @BindView(R.id.statelist)
        CardView statelist;

        public IndiaDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
