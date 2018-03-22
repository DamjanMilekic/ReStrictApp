package com.example.laptop.restrict.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop.restrict.Model.Comment;
import com.example.laptop.restrict.Model.DatumPopup;
import com.example.laptop.restrict.Model.NotificationPopup;
import com.example.laptop.restrict.R;

import java.util.ArrayList;
import java.util.List;


public class PopUpNotifAdapter extends RecyclerView.Adapter<PopUpNotifAdapter.ViewHolder> {

    private Context context;
    private List<DatumPopup> notificationList;
    private SubscribedMovieItemClickListener subscribedMovieItemClickListener;

    public PopUpNotifAdapter(Context context,List<DatumPopup> notificationList) {

        this.context=context;
        this.notificationList=notificationList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public TextView notification_item;

        public ViewHolder(View itemView) {
            super(itemView);

            notification_item = itemView.findViewById(R.id.txtNotification);

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

        final DatumPopup model = notificationList.get(position);

        Comment modelComment = model.getComment();
        holder.notification_item.setText(modelComment.getText());




    }

    @Override
    public int getItemCount() {
        return notificationList.size();
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
