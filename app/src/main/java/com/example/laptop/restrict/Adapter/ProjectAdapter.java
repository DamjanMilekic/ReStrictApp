package com.example.laptop.restrict.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laptop.restrict.R;

/**
 * Created by ivandjordjevic on 5.2.18..
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {



    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.textView.setText("" + (char)(65 + position));
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textProject);
        }

    }
}
