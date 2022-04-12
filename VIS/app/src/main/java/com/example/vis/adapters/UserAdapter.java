package com.example.vis.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vis.Student.RegisterProject;
import com.example.vis.databinding.ItemProjectListBinding;
import com.example.vis.model.ProjectModel;
import com.example.vis.model.UserModel;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.holder>{
    ArrayList<ProjectModel> mDataList;
    Context mContext;

    public UserAdapter(Context context, ArrayList<ProjectModel> data) {
        this.mContext = context;
        this.mDataList = data;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProjectListBinding rowBinding = ItemProjectListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserAdapter.holder(rowBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final ProjectModel userData = mDataList.get(position);
        holder.rowBinding.nameTv.setText(userData.getProjectName());
        holder.rowBinding.slotsTv.setText(userData.getSlots()+" Slots");
        holder.rowBinding.registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,RegisterProject.class);
                intent.putExtra("email",userData.getEmail());
                intent.putExtra("name",userData.getProjectName());
                intent.putExtra("slots",userData.getSlots());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class holder extends RecyclerView.ViewHolder {
        ItemProjectListBinding rowBinding;

        public holder(ItemProjectListBinding rowBinding) {
            super(rowBinding.getRoot());
            this.rowBinding = rowBinding;
        }
    }
}
