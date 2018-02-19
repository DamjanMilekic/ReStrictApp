package com.example.laptop.restrict.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop.restrict.Fragments.FragmentAppSettingsActivity;
import com.example.laptop.restrict.Model.Aprovals.Aprovals;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.RetrofitAppSettings.ApiClientAppSettings;
import com.example.laptop.restrict.RetrofitAppSettings.ApiInterfaceAppSettings;

import retrofit2.Call;

/**
 * Created by ivandjordjevic on 5.2.18..
 */

public class ApprovedByAdapter extends RecyclerView.Adapter<ApprovedByAdapter.ApprovedByViewHolder> {

/*
    public static final int APROVED_STATIC_VALUE = 254;

    ApiInterfaceAppSettings apiInterfaceAppSettings= ApiClientAppSettings.getApiClient().
            create(ApiInterfaceAppSettings.class);
    Call<Aprovals> call = apiInterfaceAppSettings.getAprovals
            (FragmentAppSettingsActivity.APP_TOKEN, APROVED_STATIC_VALUE);

*/

    @Override
    public ApprovedByViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_by, parent, false);
        return new ApprovedByViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApprovedByViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ApprovedByViewHolder extends RecyclerView.ViewHolder {

        public ApprovedByViewHolder(View itemView) {
            super(itemView);
        }

    }

}
