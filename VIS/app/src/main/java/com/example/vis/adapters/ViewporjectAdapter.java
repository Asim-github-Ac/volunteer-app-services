package com.example.vis.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vis.Organization.TotalRegisterStd;
import com.example.vis.databinding.ItemProjectListBinding;
import com.example.vis.databinding.ViewprojecItemBinding;
import com.example.vis.model.ProjectModel;

import java.util.List;

public class ViewporjectAdapter extends RecyclerView.Adapter<ViewporjectAdapter.myHolder> {
    List<ProjectModel> projectModels;
    Context context;

    public ViewporjectAdapter(List<ProjectModel> projectModels, Context context) {
        this.projectModels = projectModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewporjectAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewprojecItemBinding rowBinding = ViewprojecItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewporjectAdapter.myHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewporjectAdapter.myHolder holder, int position) {

        ProjectModel projectModel=projectModels.get(position);
        holder.rowBinding.nameTv.setText(projectModel.getProjectName());
        holder.rowBinding.emainnumber.setText(projectModel.getEmail());
        holder.rowBinding.slotstotal.setText(projectModel.getSlots());

        holder.rowBinding.historyResultCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TotalRegisterStd.class);
                intent.putExtra("name",projectModel.getProjectName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectModels.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        ViewprojecItemBinding rowBinding;
        public myHolder(ViewprojecItemBinding rowBinding) {
            super(rowBinding.getRoot());
            this.rowBinding = rowBinding;
        }
    }
}
