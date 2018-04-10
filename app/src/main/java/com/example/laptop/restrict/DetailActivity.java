package com.example.laptop.restrict;

import android.Manifest;
import android.app.AlertDialog;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.example.laptop.restrict.Adapter.CommentAdapter;
import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.Fragments.CommentsFragment;
import com.example.laptop.restrict.Fragments.DetailImageFragment;
import com.example.laptop.restrict.Fragments.FragmentAppSettingsActivity;
import com.example.laptop.restrict.Fragments.InfoFragment;
import com.example.laptop.restrict.Fragments.LoginFragment;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.Model.Comment;
import com.example.laptop.restrict.Model.Drawing;
import com.example.laptop.restrict.Model.DrawingHome;
import com.example.laptop.restrict.Model.ProjectStatusComment;
import com.example.laptop.restrict.Model.ProjectStatusData;
import com.example.laptop.restrict.Model.ProjectStatusShare;
import com.example.laptop.restrict.Model.Version;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String STRICTAPP_URL = "https://s.strictapp.com/";
    private static Handler handler;

    // Potrebno za PROJECT DEO AKTIVNOSTI
    private RecyclerView circleRecyclerView;
    private ProjectAdapter adapter;
    private ArrayList<Version> versionList;
    public Version selectedVersion;

    AlertDialog alertDownload, alertShare;
    private MainActivity mainActivity;

    private CommentAdapter commentAdapter;
    private ActionBar actionBar;

    // ImageButton komponente DetailActivity-a
    private ImageButton info, comment, download, share, imageButtonAppsettings;
    private ImageView backButton;

    private TextView btnNumberNotification;
    private static TextView numberOfComments;

    private TextView projectSize;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment currentFragment;

    @Override
    protected void onStart() {
        super.onStart();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        handler = new Handler(getMainLooper());

        if (getIntent() != null) {

            final int drawing_id = getIntent().getIntExtra("drawing_id", -1);

            if (drawing_id != -1) {
                // Inicijalizovanje toolbar-a
                imageButtonAppsettings = (ImageButton) findViewById(R.id.btnProfileActBarSettings);

                backButton = (ImageView) findViewById(R.id.backButtonFullScreenDetail);
                btnNumberNotification =(TextView) findViewById(R.id.txNumberOfNotif);

                numberOfComments = (TextView) findViewById(R.id.numberOfComments);

                imageButtonAppsettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initFragmentAppSettings();
                    }
                });
                // btnNumberNotification.setText(""); TODO ovde ide broj notifikacija
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Definisanje RecyclerView-a
                        circleRecyclerView = (RecyclerView) findViewById(R.id.circleRecyclerView);
                        circleRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                        circleRecyclerView.setHasFixedSize(true);
                    }
                });


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

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
                        Call<ProjectStatusData> call = apiInterfaceDetails.getVersions(drawing_id, LoginFragment.api_token);
                        call.enqueue(new Callback<ProjectStatusData>() {
                            @Override
                            public void onResponse(Call<ProjectStatusData> call, Response<ProjectStatusData> response) {


                                // Preuzivanje podataka iz JSON-a sa API-a

                                if (response.body() != null) {
                                    versionList = response.body().getData().getVersions();

                                    // Ubacivanje podataka
                                    adapter = new ProjectAdapter(DetailActivity.this, versionList);
                                    Log.e("PROJECT ADAPTER", "List size: " + versionList.size());
                                    circleRecyclerView.setAdapter(adapter);

                                    // Projekat cija se podaci trebaju prikazati na prvom pokretanju aktivnosti


                                    selectedVersion = versionList.get(0);
                                    loadNumberOfComments(selectedVersion.getId());

                                    DetailImageFragment detailImageFragment = new DetailImageFragment();
                                    InfoFragment infoFragment = new InfoFragment();
                                    CommentsFragment commentsFragment = new CommentsFragment();

                                    Bundle args = new Bundle();
                                    args.putParcelable(ProjectAdapter.SELECTED_VERSION, selectedVersion);

                                    detailImageFragment.setArguments(args);
                                    infoFragment.setArguments(args);
                                    commentsFragment.setArguments(args);

                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                    transaction.replace(R.id.detailImageFragment, detailImageFragment);
                                    transaction.add(R.id.onClickButtonFragmentContainer, infoFragment);
                                    transaction.commit();
                                }

                            }

                            @Override
                            public void onFailure(Call<ProjectStatusData> call, Throwable t) {
                                // Poruka koja ce se prikazati ukoliko podaci ne budu uspesno preuzeti
                                Toast.makeText(DetailActivity.this, "Problem sa ucitavanjem podataka", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }

        }

    }

    @Override
    public void onClick(View v) {
        final Bundle args = new Bundle();
        args.putParcelable(ProjectAdapter.SELECTED_VERSION, selectedVersion);
        /*holder.circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(position);
                raw_index = position;
                notifyDataSetChanged();
            }
        });
        if(raw_index==position){
            holder.circle.setColorFilter(R.color.colorPrimary);
        }
        else {holder.circle.setColorFilter(null);
        }*/
        /*if (rawindex == v.getId())
            info.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else {
            info.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }*/
        /*info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rawindex=v.getId();

            }
        });*/


        switch(v.getId()) {
            case R.id.infoImageButton:

                handler.post(new Runnable() {
                    @Override
                    public void run() {
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
                    }
                });

                break;

            case R.id.commentsImageButton:

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CommentsFragment commentsFragment = new CommentsFragment();
                        fragmentManager = getSupportFragmentManager();

                        currentFragment = fragmentManager.findFragmentById(R.id.onClickButtonFragmentContainer);


                        if (currentFragment != null && currentFragment instanceof InfoFragment) {
                            transaction = fragmentManager.beginTransaction();
                            transaction.remove(currentFragment);
                            transaction.add(R.id.onClickButtonFragmentContainer, commentsFragment);
                            transaction.commit();
                        }
                    }
                });

                break;

            case R.id.downloadImageButton:

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showDownloadAlert();
                    }
                });

                break;

            case R.id.shareImageButton:

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showShareAlert();
                    }
                });

                break;
        }
    }

    private void showDownloadAlert() {
        final View download_alert = LayoutInflater.from(DetailActivity.this).inflate(R.layout.download_alert, null);

        projectSize = (TextView) download_alert.findViewById(R.id.project_size);
        TextView download = (TextView) download_alert.findViewById(R.id.download);
        TextView cancel = (TextView) download_alert.findViewById(R.id.cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setView(download_alert).setCancelable(false);

        alertDownload = builder.create();

        alertDownload.getWindow().setDimAmount(0.4f);

        new ReadFileMemory().execute("pdf/drawings/u1xjwIyFm9jz76nMYB2v.pdf");



        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://s.strictapp.com/pdf/drawings/u1xjwIyFm9jz76nMYB2v.pdf"));

                ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        request.setTitle("Basement plan");
                        request.setDescription("File is being download.....");

                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);

                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                        String nameOfFile = URLUtil.guessFileName("https://s.strictapp.com/pdf/drawings/u1xjwIyFm9jz76nMYB2v.pdf", null, MimeTypeMap.getFileExtensionFromUrl("https://s.strictapp.com/pdf/drawings/u1xjwIyFm9jz76nMYB2v.pdf"));

                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);

                        DownloadManager manager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);

                    } else {

                        Toast.makeText(getApplicationContext(), "Problem sa preuzimanjem fajla.", Toast.LENGTH_SHORT).show();

                    }

                }

                alertDownload.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDownload.dismiss();
            }
        });

        alertDownload.show();
    }

    private void showShareAlert() {
        final View share_alert = LayoutInflater.from(DetailActivity.this).inflate(R.layout.share_alert, null);

        final EditText inputEmail = (EditText) share_alert.findViewById(R.id.input_email);
        inputEmail.setSingleLine();
        final EditText inputNotes = (EditText) share_alert.findViewById(R.id.input_notes);

        TextView share = (TextView) share_alert.findViewById(R.id.share);
        TextView cancel = (TextView) share_alert.findViewById(R.id.cancel);


        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setView(share_alert).setCancelable(false);

        alertShare = builder.create();

        alertShare.getWindow().setDimAmount(0.4f);


        /*share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("version", 358);
                    if (FragmentAppSettingsActivity.isEmailValid(inputEmail.getText().toString())) {
                        jsonObject.put("email", inputEmail.getText().toString());
                    } else {
                        inputEmail.setText("");
                        inputEmail.setError("Unesite ispravnu mail adresu");
                    }
                    jsonObject.put("notes", inputNotes.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
                Call<ProjectStatusShare> call = apiInterfaceDetails.share(jsonObject.toString());
                call.enqueue(new Callback<ProjectStatusShare>() {
                    @Override
                    public void onResponse(Call<ProjectStatusShare> call, Response<ProjectStatusShare> response) {
                        ProjectStatusShare projectStatusShare = response.body();
                        if (projectStatusShare != null) {
                            if (projectStatusShare.getStatus() != null && projectStatusShare.getStatus().toUpperCase().equals("SUCCESS")) {
                                Toast.makeText(DetailActivity.this, "Uspesno deljenje.",Toast.LENGTH_SHORT).show();
                                alert.dismiss();
                            } else {
                                Toast.makeText(DetailActivity.this, "Neuspesno deljenje. Pokusajte ponovo.",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DetailActivity.this, "NULL.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProjectStatusShare> call, Throwable t) {
                        Toast.makeText(DetailActivity.this, "Problem sa povezivanjem.",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });*/
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShare.dismiss();
            }
        });

        alertShare.show();
    }

    private void initActionBar(){

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View v = LayoutInflater.from(this).inflate(R.layout.toolbar_layout, null);
        actionBar.setCustomView(v);
        actionBar.setDisplayShowCustomEnabled(true);

        ImageButton notification = (ImageButton)v.findViewById(R.id.btnNotificationActBar);
        ImageButton imgProfile = (ImageButton)v.findViewById(R.id.btnProfileActBar);
        TextView numberOfNotif = (TextView)v.findViewById(R.id.txNumberOfNotif);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initFragmentAppSettings(){

        handler.post(new Runnable() {
            @Override
            public void run() {
                FragmentAppSettingsActivity fragmentAppSettingsActivity = new FragmentAppSettingsActivity();

                FragmentManager fragmentManager = getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down, R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.replace(R.id.appsettingscontainer, fragmentAppSettingsActivity).commit();
            }
        });

    }

    public void destroyFragment(){
        FragmentAppSettingsActivity fragmentAppSettingsActivity = new FragmentAppSettingsActivity();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragmentAppSettingsActivity);

    }

    public static void setNumberOfComments(final int number) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                numberOfComments.setText("" + number);
            }
        });
    }

    private class ReadFileMemory extends AsyncTask<String, Integer, Integer> {

        String url;
        String base_url = "https://s.strictapp.com/";
        int byte_memory;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            byte_memory = 0;

        }

        @Override
        protected Integer doInBackground(String... strings) {

            url = base_url + strings[0];

            URL url_source;
            HttpURLConnection connection = null;

            try {
                url_source = new URL(url);
                connection = (HttpURLConnection) url_source.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                byte_memory = connection.getContentLength();
            } catch (MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "Pogresan format url andrese.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Problem sa konektovanjem na url adresu.", Toast.LENGTH_SHORT).show();
            } finally {
                connection.disconnect();
            }

            return byte_memory;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            float size = (float) (integer / 1024);

            projectSize.setText("" + size + " KB");

        }

    }


    @Override
    protected void onStop() {
       /* if (LoginFragment.api_token !=null){

        }*/
        super.onStop();
        Log.d("detail", "onStop: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("detail", "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("detail", "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("detail", "onPause: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String apiToken= LoginFragment.api_token;
        outState.putString("api",apiToken);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        savedInstanceState.getString("api");
    }

    private void loadNumberOfComments(int version_id) {
        ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
        Call<ProjectStatusComment> call = apiInterfaceDetails.getComments(version_id, LoginFragment.api_token);
        call.enqueue(new Callback<ProjectStatusComment>() {
            @Override
            public void onResponse(Call<ProjectStatusComment> call, Response<ProjectStatusComment> response) {
                // Ucitavanje komentara sa API-a, dodavanje u adapter i prikaz u recyclerview-u
                if (response.body() != null) {

                    DetailActivity.setNumberOfComments(response.body().getComments().size());
                }

            }

            @Override
            public void onFailure(Call<ProjectStatusComment> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Problem sa ucitavanjem komentara", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
