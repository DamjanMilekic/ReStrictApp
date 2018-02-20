package com.example.laptop.restrict.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.ApprovedByAdapter;
import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.ApiClientDetails;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.Model.Approval;
import com.example.laptop.restrict.Model.ProjectStatusApprovals;
import com.example.laptop.restrict.Model.ProjectStatusComment;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.Model.Version;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ivandjordjevic on 2.2.18..
 */

// Fragment za prikaz informacija na Detail stranici
public class InfoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ApprovedByAdapter adapter;

    private ArrayList<Approval> approvals;

    private TextView name, uploaded, issuedFor, textPreliminary;
    private ImageView circlePreliminary;

    private Version selectedVersion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Ucitavanje layout-a
        View view = inflater.inflate(R.layout.layout_info, container, false);

        name = (TextView) view.findViewById(R.id.textName);
        uploaded = (TextView) view.findViewById(R.id.textUploaded);
        issuedFor = (TextView) view.findViewById(R.id.textIssuedfor);
        circlePreliminary = (ImageView) view.findViewById(R.id.circlePreliminary);
        textPreliminary = (TextView) view.findViewById(R.id.textPreliminary);

        if (getArguments() != null) {
            selectedVersion = (Version) getArguments().getSerializable(ProjectAdapter.SELECTED_VERSION);
            name.setText(selectedVersion.getLabel());
            uploaded.setText(selectedVersion.getUpdatedAt());
            issuedFor.setText(selectedVersion.getIssuedFor());
        }

        String issuedForText = issuedFor.getText().toString().trim();
        if (issuedForText.toUpperCase().equals("PRELIMINARY")) {

            circlePreliminary.setImageResource(R.mipmap.circle);

            textPreliminary.setText("P");

        }

        recyclerView = (RecyclerView) view.findViewById(R.id.approvedbyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
        Call<ProjectStatusApprovals> call = apiInterfaceDetails.getApprovals(254, DetailFragment.API_KEY);
        call.enqueue(new Callback<ProjectStatusApprovals>() {
            @Override
            public void onResponse(Call<ProjectStatusApprovals> call, Response<ProjectStatusApprovals> response) {

                ProjectStatusApprovals projectStatusApprovals = response.body();
                approvals = projectStatusApprovals.getApprovals();

                adapter = new ApprovedByAdapter(getContext(), approvals);

                // Punjenje layout-a pomocu ApprovedByAdapter-a
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ProjectStatusApprovals> call, Throwable t) {
                Toast.makeText(getContext(), "Approvals problem", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
