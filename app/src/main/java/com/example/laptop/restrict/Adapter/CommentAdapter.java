package com.example.laptop.restrict.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop.restrict.DetailActivity;
import com.example.laptop.restrict.Fragments.DetailFragment;
import com.example.laptop.restrict.Model.Comment;
import com.example.laptop.restrict.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ivandjordjevic on 6.2.18..
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {

        Comment comment = comments.get(position);
        Picasso.with(context)
                .load(DetailActivity.STRICTAPP_URL + comment.getUserAvatar())
                .into(holder.image);
        holder.name.setText(comment.getFirstName() + " " + comment.getLastName());
        holder.comment.setText(comment.getText());
        holder.dateAndTime.setText(comment.getTime());

    }

    @Override
    public int getItemCount() {
            return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, comment, dateAndTime;

        public CommentViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            comment = (TextView) itemView.findViewById(R.id.textComment);
            dateAndTime = (TextView) itemView.findViewById(R.id.date_and_time);
        }

    }

}
