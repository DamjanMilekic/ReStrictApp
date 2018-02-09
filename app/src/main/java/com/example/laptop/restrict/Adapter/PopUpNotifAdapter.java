package com.example.laptop.restrict.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop.restrict.R;

import java.util.ArrayList;
import java.util.List;


public class PopUpNotifAdapter extends RecyclerView.Adapter<PopUpNotifAdapter.ViewHolder> {

    private Context context;
    private List<String> moviesList;
    private SubscribedMovieItemClickListener subscribedMovieItemClickListener;

    public PopUpNotifAdapter(Context context,List<String> movieList) {

        this.context=context;
        this.moviesList=movieList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public TextView movie_name;

        public ViewHolder(View itemView) {
            super(itemView);

            movie_name = itemView.findViewById(R.id.txtNotification);

        }

        @Override
        public void onClick(View v) {
            if (subscribedMovieItemClickListener != null) subscribedMovieItemClickListener.onSubsMovieClick(v, getAdapterPosition());

        }
    }

    @Override
    public PopUpNotifAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PopUpNotifAdapter.ViewHolder holder, int position) {

        final String model = moviesList.get(position);

        holder.movie_name.setText(model.toString());




    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setClickListener(SubscribedMovieItemClickListener itemClickListener) {
        this.subscribedMovieItemClickListener = itemClickListener;
    }

    public interface SubscribedMovieItemClickListener
    {
        void onSubsMovieClick(View view,int position);
    }
}
