package com.example.laptop.restrict.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
        transaction.replace(R.id.fragment_container, new InfoFragment());
        transaction.commit();

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.resizeFragmentContainer);

        ImageView resize = (ImageView) view.findViewById(R.id.imgResizeFront);
        resize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.resizeFragmentContainer, new ImageFragment());
                frameLayout.setVisibility(View.VISIBLE);
                transaction.commit();
                getView().setFocusableInTouchMode(true);
                getView().requestFocus();
                getView().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){ // handle back button
                            frameLayout.setVisibility(View.INVISIBLE);
                            return true;
                        }

                        return false;
                    }
                });
            }
        });

        final ImageView un_lock = (ImageView) view.findViewById(R.id.imgLockFront);
        un_lock.setOnClickListener(new View.OnClickListener() {

            int currentResource = R.mipmap.padunlock;

            @Override
            public void onClick(View v) {
                switch(currentResource) {
                    case R.mipmap.padunlock:
                        un_lock.setImageResource(R.mipmap.padlock);
                        currentResource = R.mipmap.padlock;
                        break;
                    case R.mipmap.padlock:
                        un_lock.setImageResource(R.mipmap.padunlock);
                        currentResource = R.mipmap.padunlock;
                        break;
                }
            }
        });

        ImageButton info = (ImageButton) view.findViewById(R.id.infoImageButton);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new InfoFragment());
                transaction.commit();
            }
        });

        ImageButton comments = (ImageButton) view.findViewById(R.id.commentsImageButton);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new CommentsFragment());
                transaction.commit();
            }
        });

        ImageButton download = (ImageButton) view.findViewById(R.id.downloadImageButton);
        download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final View download_alert = LayoutInflater.from(getContext()).inflate(R.layout.download_alert, null);
                TextView download = (TextView) download_alert.findViewById(R.id.download);
                TextView cancel = (TextView) download_alert.findViewById(R.id.cancel);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(download_alert).setCancelable(false);

                final AlertDialog alert = builder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

                alert.show();
            }

        });

        ImageButton share = (ImageButton) view.findViewById(R.id.shareImageButton);
        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final View share_alert = LayoutInflater.from(getContext()).inflate(R.layout.share_alert, null);
                Button cancel = (Button) share_alert.findViewById(R.id.cancel);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(share_alert).setCancelable(false);

                final AlertDialog alert = builder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

                alert.show();
            }

        });

        return view;
    }

}
