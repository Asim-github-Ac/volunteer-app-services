package com.example.vis.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vis.databinding.HistoryadapterBinding;
import com.example.vis.databinding.ViewprojecItemBinding;
import com.example.vis.model.StdRegisterProject;

import java.util.List;

public class History_adapter extends RecyclerView.Adapter<History_adapter.myHolder> {
    Context context;
    List<StdRegisterProject> list;

    public History_adapter(Context context, List<StdRegisterProject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public History_adapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryadapterBinding rowBinding = HistoryadapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new History_adapter.myHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull History_adapter.myHolder holder, int position) {
        StdRegisterProject stdRegisterProject=list.get(position);

        holder.rowBinding.nameTv.setText(stdRegisterProject.getOrgname());
        holder.rowBinding.stdemail.setText(stdRegisterProject.getOrgslots());
        holder.rowBinding.slotsTv.setText(stdRegisterProject.getStdemail());

        holder.rowBinding.historyResultCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent=new Intent(context,)
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        HistoryadapterBinding rowBinding;
        public myHolder(HistoryadapterBinding rowBinding) {
            super(rowBinding.getRoot());
            this.rowBinding = rowBinding;
        }
    }
}
