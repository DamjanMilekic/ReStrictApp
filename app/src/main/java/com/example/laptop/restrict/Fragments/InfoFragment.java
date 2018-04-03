package com.example.laptop.restrict.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.laptop.restrict.DetailActivity;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Approval;
import com.example.laptop.restrict.Model.ProjectStatusApprovals;
import com.example.laptop.restrict.Model.ProjectStatusComment;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.Model.Version;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ivandjordjevic on 2.2.18..
 */

// Fragment za prikaz informacija na Detail stranici
public class InfoFragment extends Fragment {

    // Potrebno za deo IZNAD APPROVED BY DELA
    private TextView name, uploaded, issuedFor, textPreliminary;
    private ImageView circlePreliminary;
    private Version selectedVersion;

    // Potrebno za APPROVED BY
    private RecyclerView recyclerView;
    private ApprovedByAdapter adapter;
    private ArrayList<Approval> approvals;

    private int drawing_id;
    private String stringName, stringUploaded, stringIssuedFor;

    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Ucitavanje layout-a
        final View view = inflater.inflate(R.layout.layout_info, container, false);

        // Registrovanje TextView komponenti
        name = (TextView) view.findViewById(R.id.textName);
        uploaded = (TextView) view.findViewById(R.id.textUploaded);
        issuedFor = (TextView) view.findViewById(R.id.textIssuedfor);
        circlePreliminary = (ImageView) view.findViewById(R.id.circlePreliminary);
        textPreliminary = (TextView) view.findViewById(R.id.textPreliminary);

        handler = new Handler();

        if (getArguments() != null) {

            selectedVersion = (Version) getArguments().getParcelable(ProjectAdapter.SELECTED_VERSION);

            drawing_id = selectedVersion.getId();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    stringName= selectedVersion.getLabel();
                    name.setText(stringName);
                }
            });

            /*stringUploaded=selectedVersion.getUpdatedAt();
            uploaded.setText(stringUploaded);*/
            /*SimpleDateFormat sdf = new SimpleDateFormat("yy/mm/dd hh:mm");
            uploaded.setText(sdf.format(selectedVersion.getUpdatedAt()));*/

            handler.post(new Runnable() {
                @Override
                public void run() {
                    stringUploaded=selectedVersion.getUpdatedAt();

                    try {
                        Date date = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(stringUploaded);
                        String formatedDate = new SimpleDateFormat("yy/mm/dd hh:mm").format(date);
                        uploaded.setText(formatedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            handler.post(new Runnable() {
                @Override
                public void run() {
                    stringIssuedFor = selectedVersion.getIssuedFor();
                    issuedFor.setText(stringIssuedFor);
                }
            });

        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                String issuedForText = issuedFor.getText().toString().trim();
                if (issuedForText.toUpperCase().equals("PRELIMINARY")) {

                    circlePreliminary.setImageResource(R.mipmap.circle);
                    circlePreliminary.setBackgroundColor(Color.parseColor("#81c6fd"));//dodata boja
                    textPreliminary.setText("P");

                } else {
                    // pogledati lepo prikaz za ISSUED FOR`
                }
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                recyclerView = (RecyclerView) view.findViewById(R.id.approvedbyRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);

                ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
                Call<ProjectStatusApprovals> call = apiInterfaceDetails.getApprovals(selectedVersion.getId()/*, LoginFragment.api_token*/);
                call.enqueue(new Callback<ProjectStatusApprovals>() {
                    @Override
                    public void onResponse(Call<ProjectStatusApprovals> call, Response<ProjectStatusApprovals> response) {

                        if (response.body() != null) {
                            ProjectStatusApprovals projectStatusApprovals = response.body();
                            approvals = projectStatusApprovals.getApprovals();

                            adapter = new ApprovedByAdapter(getContext(), approvals);

                            // Punjenje layout-a pomocu ApprovedByAdapter-a
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProjectStatusApprovals> call, Throwable t) {
                        Toast.makeText(getContext(), "Problem sa ucitavanjem APPROVED BY dela", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }

}
