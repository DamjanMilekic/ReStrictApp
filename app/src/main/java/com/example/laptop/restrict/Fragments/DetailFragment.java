package com.example.laptop.restrict.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.ApiClientDetails;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.ProjectStatusData;
import com.example.laptop.restrict.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.laptop.restrict.Model.Version;

// Glavni fragment Detail stranice
public class DetailFragment extends Fragment {

    public static final String API_KEY = "jdqYFmt9Qs5JfTEqvcylWT3ykizYbVCuQkvv0toDx2DrdqnzJ6aSkMwQx15g";
    public static final String STRICTAPP_URL = "https://s.strictapp.com/";

    private FragmentActivity fragmentContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private View view;

    private RecyclerView recyclerView;
    private ProjectAdapter adapter;

    private ArrayList<Version> versionList;
    public Version selectedVersion;


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
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.circleRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
        Call<ProjectStatusData> call = apiInterfaceDetails.getVersions(358, API_KEY);
        call.enqueue(new Callback<ProjectStatusData>() {
            @Override
            public void onResponse(Call<ProjectStatusData> call, Response<ProjectStatusData> response) {
                versionList = response.body().getData().getVersions();
                // Punjenje Project dela
                adapter = new ProjectAdapter(getContext(), versionList);
                Log.e("PROJECT ADAPTER", "List size: " + versionList.size());
                recyclerView.setAdapter(adapter);

                if (getArguments() != null) {
                    selectedVersion = (Version) getArguments().getParcelable(ProjectAdapter.SELECTED_VERSION);
                } else {
                    selectedVersion = versionList.get(0);
                }

                // Ucitavanje slike projekta
                ImageView projectImage = (ImageView) view.findViewById(R.id.image);

                Picasso.with(getContext())
                        .load(STRICTAPP_URL + selectedVersion.getImageFile()).resize(300,300) //RESIZE ZBOG KVALITETA
                        .into(projectImage);

                // OnClickListener za resize slike projekta
                resizeOnClick(view);

                // Default ucitavanje dela za informacije na Detail-u prilikom prvog instanciranja DetailFragment-a
                InfoFragment infoFragment = new InfoFragment();
                Bundle arguments = new Bundle();
                arguments.putParcelable(ProjectAdapter.SELECTED_VERSION, selectedVersion);
                infoFragment.setArguments(arguments);
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, infoFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                // Prikaz fragmenta za informacijama u donjem delu DetailFragmenta pritiskom na infoImageButton
                buttonInfoOnClick(view);

                // Prikaz fragmenta za komentarima u donjem delu DetailFragmenta pritiskom na commentsImageButton
                buttonCommentsOnClick(view);

            }

            @Override
            public void onFailure(Call<ProjectStatusData> call, Throwable t) {
                Toast.makeText(getContext(), "Nismo primili podatke.", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        // Default ucitavanje dela za informacije na Detail-u prilikom prvog instanciranja DetailFragment-a
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new InfoFragment());
        transaction.commit();*/

        // OnClickListener za (un)lock slike projekta
        lockUnlockOnClick(view);

        // Prikaz alert dialoga za download u DetailFragmentu pritiskom na downloadImageButton
        buttonDownloadOnClick(view);

        // Prikaz alert dialoga za share u DetailFragmentu pritiskom na sharemageButton
        buttonShareOnClick(view);

        return view;
    }

    private void buttonShareOnClick(View view) {
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
    }

    private void buttonDownloadOnClick(View view) {
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
    }

    private void buttonCommentsOnClick(View view) {
        ImageButton comments = (ImageButton) view.findViewById(R.id.commentsImageButton);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new CommentsFragment());
                transaction.commit();
            }
        });
    }

    private void buttonInfoOnClick(View view) {
        ImageButton info = (ImageButton) view.findViewById(R.id.infoImageButton);
        // Ucitavanje dela za informacije na Detail-u prilikom prvog instanciranja DetailFragment-a
        final InfoFragment infoFragment = new InfoFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ProjectAdapter.SELECTED_VERSION, selectedVersion);
        infoFragment.setArguments(arguments);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, infoFragment);
                transaction.commit();
            }
        });
    }

    private void lockUnlockOnClick(View view) {
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
    }

    private void resizeOnClick(View view) {
        ImageView resize = (ImageView) view.findViewById(R.id.imgResizeFront);

        final ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProjectAdapter.SELECTED_VERSION, selectedVersion);
        imageFragment.setArguments(bundle);

        resize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prikazivanje slike projekta pomocu ImageFragment-a na praznom FrameLayout-u u fragment_detail.xml fajlu
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
                transaction2.replace(R.id.resizeFragmentContainer, imageFragment);
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
                //Gasenje actionbara prilikom inicijalizacije fullscreen dugmeta
                MainActivity mainActivity = (MainActivity)getActivity();

                android.support.v7.app.ActionBar actionBar;

                actionBar = mainActivity.getSupportActionBar();
                actionBar.setDisplayShowHomeEnabled(false);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.hide();


            }
        });
    }
/*
    private void dataToRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.circleRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        // Punjenje Project dela
        adapter = new ProjectAdapter(getContext(), data.getVersions());

        recyclerView.setAdapter(adapter);
    }
*/

}
