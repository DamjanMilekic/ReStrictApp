package com.example.laptop.restrict.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop.restrict.Fragments.DetailFragment;
import com.example.laptop.restrict.Model.Approval;
import com.example.laptop.restrict.Model.Client;
import com.example.laptop.restrict.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ivandjordjevic on 5.2.18..
 */

public class ApprovedByAdapter extends RecyclerView.Adapter<ApprovedByAdapter.ApprovedByViewHolder> {

    private Context context;
    private ArrayList<Approval> approvals;

    public ApprovedByAdapter(Context context, ArrayList<Approval> approvals) {
        this.context = context;
        this.approvals = approvals;
    }

    @Override
    public ApprovedByViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_by, parent, false);
        return new ApprovedByViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApprovedByViewHolder holder, int position) {
        Approval approval = approvals.get(position);
        Picasso.with(context)
                .load(DetailFragment.STRICTAPP_URL + approval.getImage())
                .into(holder.image);
        holder.name.setText(approval.getFirstName() + " " + approval.getLastName());
        holder.jobPosition.setText(approval.getRole());

        if (approval.getUploader()) {

            Picasso.with(context)
                    .load(R.drawable.uploader)
                    .into(holder.isUploader);

            Picasso.with(context)
                    .load(R.drawable.check_symbol)
                    .into(holder.isChecked);

        } else if (approval.getApproved() == 0) {

            Picasso.with(context)
                    .load(R.drawable.wrong)
                    .into(holder.isChecked);

        } else if (approval.getApproved() == 1) {

            Picasso.with(context)
                    .load(R.drawable.check_symbol)
                    .into(holder.isChecked);

        } else if (approval.getApproved() == 2) {

            Picasso.with(context)
                    .load(R.drawable.question)
                    .into(holder.isChecked);

        }
    }

    @Override
    public int getItemCount() {
        return approvals.size();
    }

    public class ApprovedByViewHolder extends RecyclerView.ViewHolder {

        ImageView image, isUploader, isChecked;
        TextView name, jobPosition;

        public ApprovedByViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            jobPosition = (TextView) itemView.findViewById(R.id.jobPosition);
            isUploader = (ImageView) itemView.findViewById(R.id.isUploader);
            isChecked = (ImageView) itemView.findViewById(R.id.checked);
        }

    }

}
