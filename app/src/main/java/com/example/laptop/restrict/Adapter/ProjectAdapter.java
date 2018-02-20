package com.example.laptop.restrict.Adapter;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop.restrict.Fragments.DetailFragment;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Version;

import com.example.laptop.restrict.R;

import java.util.ArrayList;

/**
 * Created by ivandjordjevic on 5.2.18..
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    public static final String SELECTED_VERSION = "SELECTED_VERSION";

    private Context context;
    private ArrayList<Version> versions;

    public ProjectAdapter(Context context, ArrayList<Version> versions) {
        this.context = context;
        this.versions = versions;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, final int position) {
        holder.textView.setText("" + (char)(65 + position));
        holder.circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(SELECTED_VERSION, versions.get(position));
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                reopenDetailFragment(detailFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return versions.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView circle;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textProject);
            circle = (ImageView) itemView.findViewById(R.id.circle);
        }

    }

    private void reopenDetailFragment(DetailFragment detailFragment) {
        MainActivity mainActivity = (MainActivity) context;
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, detailFragment);
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_out_left, R.anim.slide_from_right,R.anim.slide_out_left);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
