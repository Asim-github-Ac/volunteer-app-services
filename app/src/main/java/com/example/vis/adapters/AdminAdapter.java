package com.example.vis.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vis.model.ProjectModel;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.myHolder> {
    Context context;
    List<ProjectModel> projectModels;

    public AdminAdapter(Context context, List<ProjectModel> projectModels) {
        this.context = context;
        this.projectModels = projectModels;
    }

    @NonNull
    @Override
    public AdminAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.myHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return projectModels.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        public myHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
