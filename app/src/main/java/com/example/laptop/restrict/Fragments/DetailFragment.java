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

// Glavni fragment Detail stranice
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

        // Punjenje Project dela
        adapter = new ProjectAdapter();

        recyclerView.setAdapter(adapter);

        // Default ucitavanje dela za informacije na Detail-u prilikom prvog instanciranja DetailFragment-a
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new InfoFragment());
        transaction.commit();

        // Ucitavanje slike projekta
        ImageView projectImage = (ImageView) view.findViewById(R.id.image);
        projectImage.setImageResource(R.drawable.landscape);

        // OnClickListener za resize slike projekta
        ImageView resize = (ImageView) view.findViewById(R.id.imgResizeFront);
        resize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prikazivanje slike projekta pomocu ImageFragment-a na praznom FrameLayout-u u fragment_detail.xml fajlu
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
                transaction2.replace(R.id.resizeFragmentContainer, new ImageFragment());
                transaction2.addToBackStack(null);
                transaction2.commit();
                getView().setFocusableInTouchMode(true);
                getView().requestFocus();

                // OnClickListener za izlazak iz resize slike
                getView().setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if( keyCode == KeyEvent.ACTION_UP) {
                            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            return true;
                        }
                        return false;
                    }

                });
            }
        });

        // OnClickListener za (un)lock slike projekta
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

        // Prikaz fragmenta za informacijama u donjem delu DetailFragmenta pritiskom na infoImageButton
        ImageButton info = (ImageButton) view.findViewById(R.id.infoImageButton);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new InfoFragment());
                transaction.commit();
            }
        });

        // Prikaz fragmenta za komentarima u donjem delu DetailFragmenta pritiskom na commentsImageButton
        ImageButton comments = (ImageButton) view.findViewById(R.id.commentsImageButton);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new CommentsFragment());
                transaction.commit();
            }
        });

        // Prikaz alert dialoga za download u DetailFragmentu pritiskom na downloadImageButton
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

        // Prikaz alert dialoga za share u DetailFragmentu pritiskom na sharemageButton
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
