package com.example.laptop.restrict.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.ApiClientDetails;
import com.example.laptop.restrict.DetailActivity;
import com.example.laptop.restrict.Fragments.DetailFragment;
import com.example.laptop.restrict.Fragments.LoginFragment;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.Model.Comment;
import com.example.laptop.restrict.Model.ProjectStatusApprovals;
import com.example.laptop.restrict.Model.ProjectStatusDeleteComment;
import com.example.laptop.restrict.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(CommentViewHolder holder, final int position) {

        final Comment comment = comments.get(position);
        Picasso.with(context)
                .load(DetailActivity.STRICTAPP_URL + comment.getUserAvatar())
                .into(holder.image);
        holder.name.setText(comment.getFirstName() + " " + comment.getLastName());
        holder.comment.setText(comment.getText());
        holder.dateAndTime.setText(comment.getTime());

        if (comment.getUserId() == LoginFragment.user_id) {
            holder.imageCircle2.setVisibility(View.VISIBLE);
            holder.imageCircle3.setVisibility(View.VISIBLE);

            holder.imageCircle3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDeleteComment(comment.getId());
                    comments.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, comments.size());
                    notifyDataSetChanged();

                }
            });

        }

        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(comment.getTime());
            String formatedDate = new SimpleDateFormat("yy/mm/dd hh:mm").format(date);
            holder.dateAndTime.setText(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
            return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView image, imageCircle1, imageCircle2, imageCircle3;
        TextView name, comment, dateAndTime;

        public CommentViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            comment = (TextView) itemView.findViewById(R.id.textComment);
            dateAndTime = (TextView) itemView.findViewById(R.id.date_and_time);
            imageCircle1 = (ImageView) itemView.findViewById(R.id.imageCircle1);
            imageCircle2 = (ImageView) itemView.findViewById(R.id.imageCircle2);
            imageCircle2.setVisibility(View.INVISIBLE);
            imageCircle3 = (ImageView) itemView.findViewById(R.id.imageCircle3);
            imageCircle3.setVisibility(View.INVISIBLE);
        }

    }

    private void callDeleteComment(int id) {
        ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
        Call<ProjectStatusDeleteComment> call = apiInterfaceDetails.deleteComment(id, LoginFragment.api_token);
        call.enqueue(new Callback<ProjectStatusDeleteComment>() {
            @Override
            public void onResponse(Call<ProjectStatusDeleteComment> call, Response<ProjectStatusDeleteComment> response) {
                if (response.body() != null) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Something went wrong. Try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProjectStatusDeleteComment> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
