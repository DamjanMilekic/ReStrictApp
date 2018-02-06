package com.example.laptop.restrict.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.R;


public class DetailFragment extends Fragment {

    private FragmentActivity fragmentContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private RecyclerView recyclerView;
    private ProjectAdapter adapter;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentContext = (FragmentActivity) context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = fragmentContext.getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.circleRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new ProjectAdapter();

        recyclerView.setAdapter(adapter);

        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, new InfoFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        ImageButton info = (ImageButton) view.findViewById(R.id.infoImageButton);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new InfoFragment());
                //transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ImageButton comments = (ImageButton) view.findViewById(R.id.commentsImageButton);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new CommentsFragment());
                //transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
