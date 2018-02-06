package com.example.laptop.restrict.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.laptop.restrict.Adapter.CommentAdapter;
import com.example.laptop.restrict.R;

/**
 * Created by ivandjordjevic on 2.2.18..
 */

public class CommentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_comments, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new CommentAdapter();

        recyclerView.setAdapter(adapter);


        return view;
    }

}
