package com.example.vis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vis.databinding.HistoryItemBinding;
import com.example.vis.databinding.HistoryadapterBinding;
import com.example.vis.databinding.ItemProjectListBinding;
import com.example.vis.model.UoloadTimer_Data;

import java.util.List;

public class ViewHistory_Adapter extends RecyclerView.Adapter<ViewHistory_Adapter.myHolder> {

    Context context;
    List<UoloadTimer_Data> data;

    public ViewHistory_Adapter(Context context, List<UoloadTimer_Data> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHistory_Adapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryItemBinding rowBinding = HistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHistory_Adapter.myHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHistory_Adapter.myHolder holder, int position) {

        UoloadTimer_Data data1=data.get(position);
        holder.rowBinding.date.setText("Date : "+data1.getDate());
        holder.rowBinding.endtimeis.setText("Email : "+data1.getEmail());
        holder.rowBinding.startdate.setText("Start Date : "+data1.getStarttime());
        if (data1.getStatus().equals("1")){
            holder.rowBinding.status.setText("Status : "+data1.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        HistoryItemBinding rowBinding;
        public myHolder(HistoryItemBinding rowBinding) {
            super(rowBinding.getRoot());
            this.rowBinding = rowBinding;

        }
    }
}

