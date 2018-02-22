package com.example.laptop.restrict.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop.restrict.Model.Aprovals.DataAprovals;
import com.example.laptop.restrict.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivandjordjevic on 5.2.18..
 */

public class ApprovedByAdapter extends RecyclerView.Adapter<ApprovedByAdapter.ApprovedByViewHolder> {



    private List<DataAprovals> list;
    private Context context;

    public ApprovedByAdapter(List<DataAprovals> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ApprovedByViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_by, parent, false);
        return new ApprovedByViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApprovedByViewHolder holder, int position) {
        holder.name.setText(list.get(position).getFirstName());
        holder.jobPosition.setText(list.get(position).getRole());

        String loadSource = "https://s.strictapp.com/" + list.get(position).getImage();

        Picasso.with(context)
                .load(loadSource).fit().centerCrop()
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ApprovedByViewHolder extends RecyclerView.ViewHolder {

        ImageView image, isUploader, checked;
        TextView name, jobPosition;

        public ApprovedByViewHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.image);
            //isUploader = (ImageView)itemView.findViewById(R.id.isUploader);
            //checked = (ImageView)itemView.findViewById(R.id.checked);
            name = (TextView)itemView.findViewById(R.id.name);
            jobPosition = (TextView)itemView.findViewById(R.id.jobPosition);


        }

    }

}
