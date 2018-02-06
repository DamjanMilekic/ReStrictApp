package com.example.laptop.restrict.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop.restrict.R;

/**
 * Created by ivandjordjevic on 6.2.18..
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        public CommentViewHolder(View itemView) {
            super(itemView);
        }

    }

}
