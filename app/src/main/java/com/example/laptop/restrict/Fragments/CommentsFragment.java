package com.example.laptop.restrict.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.CommentAdapter;
import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.ApiClientDetails;
import com.example.laptop.restrict.DetailActivity;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Comment;
import com.example.laptop.restrict.Model.Version;
import com.example.laptop.restrict.Model.PostCommentRequest;
import com.example.laptop.restrict.Model.ProjectStatusComment;
import com.example.laptop.restrict.Model.ProjectStatusPostComment;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.SecondLevelExpandableListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ivandjordjevic on 2.2.18..
 */

// Fragment za prikaz komentara na Detail stranici
public class CommentsFragment extends Fragment {

    // Potrebno za deo koji prikazuje komentare
    private RecyclerView recyclerView;
    private ArrayList<Comment> comments;
    private CommentAdapter adapter;

    // Potrebno za deo pretrage i slanje komentara
    private EditText search, writeComment;
    private ImageView searchButton;
    private ImageView sendButton;

    private ApiInterfaceDetails apiInterfaceDetails;

    // Potrebno za update UI-a
    private Handler handler;

    public static int version_id;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        // Ucitavanje layout-a
        View view = inflater.inflate(R.layout.layout_comments, container, false);

        handler = new Handler();

        // Omogucuje kucanje teksta u EditText komponenti u jednom redu
        search = (EditText) view.findViewById(R.id.search);
        search.setSingleLine();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (search.getText().toString() != null) {
                    apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
                    Call<ProjectStatusComment> call = apiInterfaceDetails.getComments(version_id, LoginFragment.api_token);
                    call.enqueue(new Callback<ProjectStatusComment>() {
                        @Override
                        public void onResponse(Call<ProjectStatusComment> call, Response<ProjectStatusComment> response) {
                            // Ucitavanje komentara sa API-a, dodavanje u adapter i prikaz u recyclerview-u
                            if (response.body() != null) {
                                comments = response.body().getComments();

                                final ArrayList<Comment> searchedComment = new ArrayList<>();
                                if (comments.size() > 0) {
                                    for (int i = 0; i < comments.size(); i++) {
                                        if (comments.get(i).getText().contains(search.getText().toString())) {
                                            searchedComment.add(comments.get(i));
                                        }
                                    }
                                }
                                if (searchedComment.size() > 0) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter = new CommentAdapter(getContext(), searchedComment);
                                            recyclerView.setAdapter(adapter);
                                        }
                                    });
                                }
                                DetailActivity.setNumberOfComments(searchedComment.size());
                            }

                        }

                        @Override
                        public void onFailure(Call<ProjectStatusComment> call, Throwable t) {
                            Toast.makeText(getContext(), "Problem sa ucitavanjem komentara", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*searchButton = (ImageView) view.findViewById(R.id.search_comments);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (search.getText().toString() != null) {
                    apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
                    Call<ProjectStatusComment> call = apiInterfaceDetails.getComments(254, LoginFragment.api_token);
                    call.enqueue(new Callback<ProjectStatusComment>() {
                        @Override
                        public void onResponse(Call<ProjectStatusComment> call, Response<ProjectStatusComment> response) {
                            // Ucitavanje komentara sa API-a, dodavanje u adapter i prikaz u recyclerview-u
                            comments = response.body().getComments();

                            final ArrayList<Comment> searchedComment = new ArrayList<>();
                            if (comments.size() > 0) {
                                for (int i = 0; i <comments.size(); i++) {
                                    if (comments.get(i).getText().contains(search.getText().toString())){
                                        searchedComment.add(comments.get(i));
                                    }
                                }
                            }
                            if (searchedComment.size() > 0 ) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter = new CommentAdapter(getContext(), searchedComment);
                                        recyclerView.setAdapter(adapter);
                                        search.setText("");
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "No comments to show", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ProjectStatusComment> call, Throwable t) {
                            Toast.makeText(getContext(), "Problem sa ucitavanjem komentara", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });*/


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
        Call<ProjectStatusComment> call = apiInterfaceDetails.getComments(version_id, LoginFragment.api_token);
        call.enqueue(new Callback<ProjectStatusComment>() {
            @Override
            public void onResponse(Call<ProjectStatusComment> call, Response<ProjectStatusComment> response) {
                // Ucitavanje komentara sa API-a, dodavanje u adapter i prikaz u recyclerview-u
                if (response.body() != null) {
                    comments = response.body().getComments();
                    DetailActivity.setNumberOfComments(comments.size());
                    adapter = new CommentAdapter(getContext(), comments);
                    recyclerView.swapAdapter(adapter, false);
                } else {
                    Toast.makeText(getContext(), "Nema komentara za prikaz", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProjectStatusComment> call, Throwable t) {
                Toast.makeText(getContext(), "Problem sa ucitavanjem komentara", Toast.LENGTH_SHORT).show();
            }
        });

        // Registrovanje dela za unos komentara
        writeComment = (EditText) view.findViewById(R.id.write_comment);
        sendButton = (ImageView) view.findViewById(R.id.send_button);

        // Osluskivac za unos komentara
        sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postComment(version_id);
                }
            });

        return view;
    }

    public void postComment(final int version_id) {
        String text = writeComment.getText().toString();
        writeComment.setText(null);

        if (text != null && !text.equals("")) {

            PostCommentRequest postCommentRequest = new PostCommentRequest(version_id, text, LoginFragment.api_token);
            Call<ProjectStatusPostComment> call = apiInterfaceDetails.setComment(postCommentRequest);
            call.enqueue(new Callback<ProjectStatusPostComment>() {
                @Override
                public void onResponse(Call<ProjectStatusPostComment> call, Response<ProjectStatusPostComment> response) {
                    ProjectStatusPostComment projectStatusPostComment = response.body();
                    if (projectStatusPostComment != null && projectStatusPostComment.getStatus().equals("success")) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                Call<ProjectStatusComment> reloadComments = apiInterfaceDetails.getComments(version_id, LoginFragment.api_token);
                                reloadComments.enqueue(new Callback<ProjectStatusComment>() {
                                    @Override
                                    public void onResponse(Call<ProjectStatusComment> call, Response<ProjectStatusComment> response) {
                                        // Ucitavanje komentara sa API-a, dodavanje u adapter i prikaz u recyclerview-u
                                        comments = response.body().getComments();
                                        DetailActivity.setNumberOfComments(comments.size());
                                        adapter = new CommentAdapter(getContext(), comments);
                                        recyclerView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onFailure(Call<ProjectStatusComment> call, Throwable t) {
                                        Toast.makeText(getContext(), "Problem sa internet konekcijom.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });

                        // TODO napraviti posebnu klasu za komentare i izmeniti fragment

                        /*comments.add();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, comments.size());
                        notifyDataSetChanged();*/

                        Toast.makeText(getContext(), projectStatusPostComment.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Problem sa postavljanjem komentara komentara", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProjectStatusPostComment> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {

            Toast.makeText(getContext(), "Empty field. Please, type comment.", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
