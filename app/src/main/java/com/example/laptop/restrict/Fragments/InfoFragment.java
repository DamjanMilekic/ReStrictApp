package com.example.laptop.restrict.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop.restrict.Adapter.ApprovedByAdapter;
import com.example.laptop.restrict.R;

/**
 * Created by ivandjordjevic on 2.2.18..
 */

// Fragment za prikaz informacija na Detail stranici
public class InfoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ApprovedByAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Ucitavanje layout-a
        View view = inflater.inflate(R.layout.layout_info, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.approvedbyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new ApprovedByAdapter();

        // Punjenje layout-a pomocu ApprovedByAdapter-a
        recyclerView.setAdapter(adapter);

        return view;
    }
}
