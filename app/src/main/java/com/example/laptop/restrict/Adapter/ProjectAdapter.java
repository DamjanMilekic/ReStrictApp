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

import com.example.laptop.restrict.DetailActivity;
import com.example.laptop.restrict.Fragments.CommentsFragment;
import com.example.laptop.restrict.Fragments.DetailImageFragment;
import com.example.laptop.restrict.Fragments.InfoFragment;
import com.example.laptop.restrict.Model.Version;

import com.example.laptop.restrict.R;

import java.util.ArrayList;

import static com.example.laptop.restrict.DetailActivity.setSelectedVersion;

/**
 * Created by ivandjordjevic on 5.2.18..
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    public static final String SELECTED_VERSION = "SELECTED_VERSION";
    public static final String TAG ="PORUKA";
    public int raw_index;

    private Context context;
    private ArrayList<Version> versions;

    public ProjectAdapter(Context context, ArrayList<Version> versions) {
        this.context = context;
        this.versions = versions;
        if (versions.size() > 0) {
            CommentsFragment.version_id = versions.get(versions.size()-1).getId();
        }
        raw_index = versions.size()-1;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProjectViewHolder holder, final int position) {

        holder.textView.setText("" + (char)(65 + position));
        holder.circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raw_index = position;
                updateComments(raw_index);
                updateInfo(raw_index);
                updateImage(raw_index);
                notifyDataSetChanged();
                CommentsFragment.version_id = versions.get(position).getId();
                setSelectedVersion(versions.get(position));
                DetailActivity.setDefaultColorForAllButtons();

            }
        });

        if(raw_index == position){
           // holder.circle.setColorFilter(context.getResources().getColor(R.color.strictBlue));
            holder.circle.setBackground(context.getResources().getDrawable(R.drawable.circle_selected));
        }
        else {
            holder.circle.setBackground(null);
        }

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

    // Metoda koja azurira DetailActivity
  /*  private void update(int position) {
        DetailImageFragment detailImageFragment = new DetailImageFragment();
        InfoFragment infoFragment = new InfoFragment();
        CommentsFragment commentsFragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ProjectAdapter.SELECTED_VERSION, versions.get(position));
        detailImageFragment.setArguments(args);
       // infoFragment.setArguments(args);
        commentsFragment.setArguments(args);
        DetailActivity activity = (DetailActivity) context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.detailImageFragment, detailImageFragment);
        *//*if (infoFragment.isAdded()) {
            transaction.replace(R.id.onClickButtonFragmentContainer, infoFragment);
        }else if (commentsFragment.isAdded()) {
            transaction.replace(R.id.onClickButtonFragmentContainer, commentsFragment);
        }*//*
        transaction.replace(R.id.onClickButtonFragmentContainer, infoFragment);

        transaction.commit();
    }*/

    private void update(int position) {
        DetailImageFragment detailImageFragment = new DetailImageFragment();
        InfoFragment infoFragment = new InfoFragment();
        CommentsFragment commentsFragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ProjectAdapter.SELECTED_VERSION, versions.get(position));
        detailImageFragment.setArguments(args);
        DetailActivity activity = (DetailActivity) context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.detailImageFragment, detailImageFragment);
        transaction.commit();
    }

    private void updateImage(int position) {
        DetailImageFragment detailImageFragment = new DetailImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ProjectAdapter.SELECTED_VERSION, versions.get(position));
        detailImageFragment.setArguments(args);
        DetailActivity activity = (DetailActivity) context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.detailImageFragment, detailImageFragment);

        transaction.commit();
    }

    private void updateInfo(int position) {
        InfoFragment infoFragment = new InfoFragment();


        Bundle args = new Bundle();
        args.putParcelable(ProjectAdapter.SELECTED_VERSION, versions.get(position));

        infoFragment.setArguments(args);

        DetailActivity activity = (DetailActivity) context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.onClickButtonFragmentContainer, infoFragment);

        transaction.commit();
    }

    private void updateComments(int position) {
        CommentsFragment commentsFragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ProjectAdapter.SELECTED_VERSION, versions.get(position));
        commentsFragment.setArguments(args);
        DetailActivity activity = (DetailActivity) context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.onClickButtonFragmentContainer, commentsFragment);

        transaction.commit();
    }
}
