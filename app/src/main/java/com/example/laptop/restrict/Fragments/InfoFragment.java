package com.example.laptop.restrict.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.ApprovedByAdapter;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Aprovals.Aprovals;
import com.example.laptop.restrict.Model.Aprovals.DataAprovals;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.RetrofitAppSettings.Client;
import com.example.laptop.restrict.RetrofitAppSettings.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

/**
 * Created by ivandjordjevic on 2.2.18..
 */

// Fragment za prikaz informacija na Detail stranici
public class InfoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ApprovedByAdapter adapter;
    private List<DataAprovals> dataAprovals;

    public static final int APROVED_STATIC_VALUE = 254;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Ucitavanje layout-a
        View view = inflater.inflate(R.layout.layout_info, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.approvedbyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);


        Service service = Client.getApiClient().create(Service.class);
        Call<Aprovals> call = service.getAprovals(254, MainActivity.APP_TOKEN);
        call.enqueue(new Callback<Aprovals>() {
            @Override
            public void onResponse(Call<Aprovals> call, Response<Aprovals> response) {
                Aprovals aprovals = response.body();
                //List<DataAprovals> getAllAprovals = response.body().getData();
                dataAprovals = aprovals.getData();
                adapter = new ApprovedByAdapter(dataAprovals, getContext());

                // Punjenje layout-a pomocu ApprovedByAdapter-a
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Aprovals> call, Throwable t) {
                Log.d("tag", "onFailure: geska u falior");
                Toast.makeText(getContext(),"123", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}
