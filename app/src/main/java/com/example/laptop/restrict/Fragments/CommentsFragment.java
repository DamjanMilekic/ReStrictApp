package com.example.laptop.restrict.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.CommentAdapter;
import com.example.laptop.restrict.ApiClientDetails;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.Model.Comment;
import com.example.laptop.restrict.Model.PostCommentRequest;
import com.example.laptop.restrict.Model.ProjectStatusComment;
import com.example.laptop.restrict.Model.ProjectStatusPostComment;
import com.example.laptop.restrict.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ivandjordjevic on 2.2.18..
 */

// Fragment za prikaz komentara na Detail stranici
public class CommentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;

    private EditText search, writeComment;
    private ImageView sendButton;

    private ArrayList<Comment> comments;

    private ApiInterfaceDetails apiInterfaceDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        // Ucitavanje layout-a
        View view = inflater.inflate(R.layout.layout_comments, container, false);

        search = (EditText) view.findViewById(R.id.search);
        search.setSingleLine();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
        Call<ProjectStatusComment> call = apiInterfaceDetails.getComments(254, DetailFragment.API_KEY);
        call.enqueue(new Callback<ProjectStatusComment>() {
            @Override
            public void onResponse(Call<ProjectStatusComment> call, Response<ProjectStatusComment> response) {

                ProjectStatusComment projectStatusComment = response.body();

                comments = projectStatusComment.getComments();

                adapter = new CommentAdapter(getContext(), comments);

                // Punjenje layout-a pomocu CommentAdapter-a
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ProjectStatusComment> call, Throwable t) {
                Toast.makeText(getContext(), "Problem sa ucitavanjem komentara", Toast.LENGTH_SHORT).show();
            }
        });

        writeComment = (EditText) view.findViewById(R.id.write_comment);
        sendButton = (ImageView) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });

        return view;
    }

    public void postComment() {
        String text = writeComment.getText().toString();
        PostCommentRequest postCommentRequest = new PostCommentRequest(254, text, DetailFragment.API_KEY);
        Call<ProjectStatusPostComment> call = apiInterfaceDetails.setComment(postCommentRequest);
        call.enqueue(new Callback<ProjectStatusPostComment>() {
            @Override
            public void onResponse(Call<ProjectStatusPostComment> call, Response<ProjectStatusPostComment> response) {
                ProjectStatusPostComment projectStatusPostComment = response.body();
                if (projectStatusPostComment.getStatus().equals("success")) {
                    Toast.makeText(getContext(), projectStatusPostComment.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProjectStatusPostComment> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
