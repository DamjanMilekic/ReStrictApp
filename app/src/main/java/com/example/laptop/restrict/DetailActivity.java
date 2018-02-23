package com.example.laptop.restrict;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.Fragments.CommentsFragment;
import com.example.laptop.restrict.Fragments.DetailImageFragment;
import com.example.laptop.restrict.Fragments.InfoFragment;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.Model.ProjectStatusData;
import com.example.laptop.restrict.Model.Version;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String STRICTAPP_URL = "https://s.strictapp.com/";

    // Potrebno za PROJECT DEO AKTIVNOSTI
    private RecyclerView circleRecyclerView;
    private ProjectAdapter adapter;
    private ArrayList<Version> versionList;
    public Version selectedVersion;

    // ImageButton komponente DetailActivity-a
    ImageButton info, comment, download, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Definisanje RecyclerView-a
        circleRecyclerView = (RecyclerView) findViewById(R.id.circleRecyclerView);
        circleRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        circleRecyclerView.setHasFixedSize(true);

        // Registrovanje ImageButton komponenti
        info = (ImageButton) findViewById(R.id.infoImageButton);
        comment = (ImageButton) findViewById(R.id.commentsImageButton);
        download = (ImageButton) findViewById(R.id.downloadImageButton);
        share = (ImageButton) findViewById(R.id.shareImageButton);

        // Postavljanje osluskivaca na ImmageButton komponente
        info.setOnClickListener(this);
        comment.setOnClickListener(this);
        download.setOnClickListener(this);
        share.setOnClickListener(this);

        ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
        Call<ProjectStatusData> call = apiInterfaceDetails.getVersions(358, MainActivity.APP_TOKEN);
        call.enqueue(new Callback<ProjectStatusData>() {
            @Override
            public void onResponse(Call<ProjectStatusData> call, Response<ProjectStatusData> response) {

                // Preuzivanje podataka iz JSON-a sa API-a
                versionList = response.body().getData().getVersions();

                // Ubacivanje podataka
                adapter = new ProjectAdapter(DetailActivity.this, versionList);
                Log.e("PROJECT ADAPTER", "List size: " + versionList.size());
                circleRecyclerView.setAdapter(adapter);

                // Projekat cija se podaci trebaju prikazati na prvom pokretanju aktivnosti
                selectedVersion = versionList.get(0);


                DetailImageFragment detailImageFragment = new DetailImageFragment();
                InfoFragment infoFragment = new InfoFragment();

                Bundle args = new Bundle();
                args.putParcelable(ProjectAdapter.SELECTED_VERSION, selectedVersion);

                detailImageFragment.setArguments(args);
                infoFragment.setArguments(args);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.detailImageFragment, detailImageFragment);
                transaction.add(R.id.onClickButtonFragmentContainer, infoFragment);
                transaction.commit();

            }

            @Override
            public void onFailure(Call<ProjectStatusData> call, Throwable t) {
                // Poruka koja ce se prikazati ukoliko podaci ne budu uspesno preuzeti
                Toast.makeText(DetailActivity.this, "Problem sa ucitavanjem podataka", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager;
        FragmentTransaction transaction;
        Fragment currentFragment;

        Bundle args = new Bundle();
        args.putParcelable(ProjectAdapter.SELECTED_VERSION, selectedVersion);

        switch(v.getId()) {
            case R.id.infoImageButton:
                InfoFragment infoFragment = new InfoFragment();
                infoFragment.setArguments(args);
                fragmentManager = getSupportFragmentManager();

                currentFragment = fragmentManager.findFragmentById(R.id.onClickButtonFragmentContainer);

                if (currentFragment != null && currentFragment instanceof CommentsFragment) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(currentFragment);
                    transaction.add(R.id.onClickButtonFragmentContainer, infoFragment);
                    transaction.commit();
                }
                break;

            case R.id.commentsImageButton:
                CommentsFragment commentsFragment = new CommentsFragment();
                fragmentManager = getSupportFragmentManager();

                currentFragment = fragmentManager.findFragmentById(R.id.onClickButtonFragmentContainer);

                if (currentFragment != null && currentFragment instanceof InfoFragment) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(currentFragment);
                    transaction.add(R.id.onClickButtonFragmentContainer, commentsFragment);
                    transaction.commit();
                }
                break;

            case R.id.downloadImageButton:
                showDownloadAlert();
                break;

            case R.id.shareImageButton:
                showShareAlert();
                break;
        }
    }

    private void showDownloadAlert() {
        View download_alert = LayoutInflater.from(DetailActivity.this).inflate(R.layout.download_alert, null);

        TextView download = (TextView) download_alert.findViewById(R.id.download);
        TextView cancel = (TextView) download_alert.findViewById(R.id.cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
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

    private void showShareAlert() {
        View share_alert = LayoutInflater.from(DetailActivity.this).inflate(R.layout.share_alert, null);

        EditText inputEmail = (EditText) share_alert.findViewById(R.id.input_email);
        inputEmail.setSingleLine();

        Button cancel = (Button) share_alert.findViewById(R.id.cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
