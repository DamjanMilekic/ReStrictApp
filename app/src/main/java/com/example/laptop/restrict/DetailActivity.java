package com.example.laptop.restrict;

import android.Manifest;
import android.app.AlertDialog;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import java.util.List;

import com.example.laptop.restrict.Adapter.CommentAdapter;
import com.example.laptop.restrict.Adapter.PopUpNotifAdapter;
import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.Fragments.CommentsFragment;
import com.example.laptop.restrict.Fragments.DetailImageFragment;
import com.example.laptop.restrict.Fragments.FragmentAppSettingsActivity;
import com.example.laptop.restrict.Fragments.InfoFragment;
import com.example.laptop.restrict.Fragments.LoginFragment;
import com.example.laptop.restrict.Interfaces.ApiInterfaceDetails;
import com.example.laptop.restrict.Model.Comment;
import com.example.laptop.restrict.Model.DatumPopup;
import com.example.laptop.restrict.Model.Drawing;
import com.example.laptop.restrict.Model.DrawingHome;
import com.example.laptop.restrict.Model.NotificationPopup;
import com.example.laptop.restrict.Model.ProjectStatusComment;
import com.example.laptop.restrict.Model.ProjectStatusData;
import com.example.laptop.restrict.Model.ProjectStatusShare;
import com.example.laptop.restrict.Model.TypePopup;
import com.example.laptop.restrict.Model.Version;
import com.example.laptop.restrict.RetrofitAppSettings.Client;
import com.example.laptop.restrict.RetrofitAppSettings.Service;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

import static com.example.laptop.restrict.Fragments.HomeFragment.applyDim;
import static com.example.laptop.restrict.Fragments.HomeFragment.clearDim;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, PopUpNotifAdapter.PopupItemClickListener {

    public static final String STRICTAPP_URL = "https://s.strictapp.com/";
    private static Handler handler;

    // Potrebno za PROJECT DEO AKTIVNOSTI
    private RecyclerView circleRecyclerView;
    private ProjectAdapter adapter;
    private ArrayList<Version> versionList;
    private static Version selectedVersion;

    private AlertDialog alertDownload, alertShare;
    private MainActivity mainActivity;

    private CommentAdapter commentAdapter;
    private ActionBar actionBar;

    // ImageButton komponente DetailActivity-a
    private ImageButton info, comment, download, share;
    CircleImageView imageButtonAppsettings;
    private ImageView backButton;

    private TextView btnNumberNotification;
    private static TextView numberOfComments;

    private TextView projectSize;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment currentFragment;


    private ImageView notification;

    private List<DatumPopup> notifList = new ArrayList<>();
    private Point p;
    private Global globalVar;
    private PopUpNotifAdapter notifAdapter;

    private int drawing_id = -1;
    private TextView projectName;

    private static Resources resources;

    public static FrameLayout frameLayout;
    @Override
    protected void onStart() {
        super.onStart();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();

        resources = getResources();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        frameLayout = (FrameLayout)findViewById(R.id.appsettingscontainer);


        /*getFragmentManager().
                beginTransaction().
                remove(getFragmentManager().
                        findFragmentById(R.id.appsettingscontainer)).
                commit();*/
        handler = new Handler(getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                actionBarInit();
            }
        });

        if (getIntent() != null) {

            drawing_id = getIntent().getIntExtra("drawing_id", -1);


            if (drawing_id != -1) {
                // Inicijalizovanje toolbar-a
                imageButtonAppsettings = (CircleImageView) findViewById(R.id.btnProfileActBarSettings);
                //postavljanje slicice na toolbaru
                String urlSLika= "https://s.strictapp.com/" + SavedSharedPreferences.getPrefAvatar(DetailActivity.this);
                Picasso.with(this)
                        .load(urlSLika).fit().centerCrop()
                        .into(imageButtonAppsettings);

                backButton = (ImageView) findViewById(R.id.backButtonFullScreenDetail);


                numberOfComments = (TextView) findViewById(R.id.numberOfComments);

                /*imageButtonAppsettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initFragmentAppSettings();
                    }
                });*/

                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                        FragmentManager fm = getSupportFragmentManager();

                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();

                        }
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

                setButtonInFocus(info);

                // Postavljanje osluskivaca na ImmageButton komponente
                info.setOnClickListener(this);
                comment.setOnClickListener(this);
                download.setOnClickListener(this);
                share.setOnClickListener(this);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
                        Call<ProjectStatusData> call = apiInterfaceDetails.getVersions(drawing_id, SavedSharedPreferences.getAPIToken(DetailActivity.this));
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


                                    selectedVersion = versionList.get(versionList.size()-1);
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

        switch(v.getId()) {
            case R.id.infoImageButton:
                setDefaultButtonColor(comment, download, share);
                setButtonInFocus(info);
                numberOfComments.setTextColor(getResources().getColor(R.color.buttonsSettings));
                numberOfComments.setAlpha(0.6f);
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
                setDefaultButtonColor(info, download, share);
                setButtonInFocus(comment);
                numberOfComments.setTextColor(getResources().getColor(R.color.colorPrimary));
                numberOfComments.setAlpha(1.0f);
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
                        showDownloadAlert(selectedVersion.getLabel(), selectedVersion.getPdfFile());
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

    private void showDownloadAlert(final String fileName, final String path) {

        if (path != null && !path.equals("")) {

            final View download_alert = LayoutInflater.from(DetailActivity.this).inflate(R.layout.download_alert, null);

            projectName = (TextView) download_alert.findViewById(R.id.project_name);
            projectSize = (TextView) download_alert.findViewById(R.id.project_size);
            TextView download = (TextView) download_alert.findViewById(R.id.download);
            TextView cancel = (TextView) download_alert.findViewById(R.id.cancel);

            projectName.setText(fileName);

            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setView(download_alert).setCancelable(false);

            alertDownload = builder.create();

            alertDownload.getWindow().setDimAmount(0.4f);

            new ReadFileMemory().execute(path);


            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://s.strictapp.com/" + path));

                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            request.setTitle(fileName);
                            request.setDescription("File is being download.....");

                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);

                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                            String nameOfFile = URLUtil.guessFileName("https://s.strictapp.com/" + path, null, MimeTypeMap.getFileExtensionFromUrl("https://s.strictapp.com/" + path));

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

        } else {

            Toast.makeText(DetailActivity.this, "Nothing to download.", Toast.LENGTH_SHORT).show();

        }
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

/*
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
*/

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

               // fragmentTransaction.setCustomAnimations(R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down, R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down);
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

    @Override
    public void onPopClick(View view, int position) {

        DatumPopup notification = notifList.get(position);


        if(Integer.valueOf(notification.getTypeId()) == 1 )
        {
            Comment comment = notification.getComment();
            //ovde treba dodati metodu koja vodi do tog komentara na stranici detalji
            Toast.makeText(DetailActivity.this, "Ovo vodi na komentar", Toast.LENGTH_SHORT).show();
        }
        else if(Integer.valueOf(notification.getTypeId()) == 2 )
        {
            TypePopup typeNotif = notification.getType();
            Toast.makeText(DetailActivity.this, "Ovo vodi na crtez", Toast.LENGTH_SHORT).show();
            //a ovde do crteza
        }

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
        String apiToken= SavedSharedPreferences.getAPIToken(DetailActivity.this);
        outState.putString("api",apiToken);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        savedInstanceState.getString("api");
    }

    private void loadNumberOfComments(int version_id) {
        ApiInterfaceDetails apiInterfaceDetails = ApiClientDetails.getApiClient().create(ApiInterfaceDetails.class);
        Call<ProjectStatusComment> call = apiInterfaceDetails.getComments(version_id,SavedSharedPreferences.getAPIToken(DetailActivity.this));
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

    public void actionBarInit() {
       /* actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(DetailActivity.this);

        View mCustomView = mInflater.inflate(R.layout.toolbar_layout, null);

        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        notification = findViewById(R.id.btnNotificationActBar);
        imgProfile = findViewById(R.id.btnProfileActBar);
        numberOfNotif = findViewById(R.id.txNumberOfNotif);

        if(notifList.size()==0)
        {
            getNotification();
        }



        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                initFragmentAppSettings();
                actionBar.hide();

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //  notification.setEnabled(false);
                if (notifList.size() > 0) {
                    showPopUp(DetailActivity.this, p, v, notifList);


                } else {


                    getNotification();

                }
                // notification.setEnabled(true);


            }
        });

        if (notifList.size() > 0) {
            numberOfNotif.setText(Integer.toString(notifList.size()));
        }
        *//*actionBar.show();*/

        btnNumberNotification =(TextView) findViewById(R.id.txNumberOfNotif);
        notification = findViewById(R.id.btnNotificationActBar);


        // imgProfile = findViewById(R.id.btnProfileActBar);
        if(notifList.size()==0)
        {
            getNotification();
        }



        imageButtonAppsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                initFragmentAppSettings();
                //actionBar.hide();

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //  notification.setEnabled(false);
                if (notifList.size() > 0) {
                    showPopUp(DetailActivity.this, p, v, notifList);


                } else {


                    getNotification();

                }
                // notification.setEnabled(true);


            }
        });

        if (notifList.size() > 0) {
            btnNumberNotification.setText(Integer.toString(notifList.size()));
        }

    }
    private void showPopUp(Context context, Point p, View view, List<DatumPopup> notifList) {

        View popupView = getLayoutInflater().inflate(R.layout.popup_notifications, null);
        RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.rvNotify);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(popupView);



        int location[] = new int[2];
        int OFFSET_X = 0;
        int OFFSET_Y = 0;

        // view.getLocationOnScreen(location);
        notifAdapter = new PopUpNotifAdapter(DetailActivity.this, notifList);
        notifAdapter.setClickListener(DetailActivity.this);
        recyclerView.setAdapter(notifAdapter);

        LinearLayoutManager vertical
                = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(vertical);
        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

        applyDim(root,0.7f);

        popupWindow.setAnimationStyle(R.style.popup_scale_animation);
        popupWindow.showAsDropDown(view, p.x + OFFSET_X, p.y + OFFSET_Y);


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(root);

            }
        });


    }

    private void getActionBarHeight(){
        TypedValue tv = new TypedValue();
        if (this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            //actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
    }

    private void getNotification() {

        Service apiService = Client.getApiClient().create(Service.class);
        Call<NotificationPopup> call = apiService.getNotificationsPopup(SavedSharedPreferences.getAPIToken(DetailActivity.this));
        globalVar = (Global) getApplicationContext();
        call.enqueue(new Callback<NotificationPopup>() {

            @Override
            public void onResponse(Call<NotificationPopup> call, Response<NotificationPopup> response) {

                if (response.body() != null) {
                    NotificationPopup notificationPopup = response.body();
                    notifList = notificationPopup.getData();


                    p = globalVar.getPopupPoint();

                    btnNumberNotification.setText(String.valueOf(notifList.size()));
                }


            }

            @Override
            public void onFailure(Call<NotificationPopup> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Fail on server", Toast.LENGTH_LONG).show();

            }
        });

        //actionBar.show();



    }

    private void setDefaultButtonColor(ImageButton imageButton1, ImageButton imageButton2, ImageButton imageButton3) {

        imageButton1.setAlpha(0.6f);
        imageButton1.setColorFilter(getResources().getColor(R.color.buttonsSettings));

        imageButton2.setAlpha(0.6f);
        imageButton2.setColorFilter(getResources().getColor(R.color.buttonsSettings));

        imageButton3.setAlpha(0.6f);
        imageButton3.setColorFilter(getResources().getColor(R.color.buttonsSettings));

    }

    private void setButtonInFocus(ImageButton imageButton) {

        imageButton.setAlpha(1.0f);
        imageButton.setColorFilter(getResources().getColor(R.color.colorPrimary));

    }

/*
    public static void setDefaultColorForAllButtons() {

        info.setAlpha(1.0f);
        info.setColorFilter(resources.getColor(R.color.colorPrimary));

        comment.setAlpha(0.6f);
        comment.setColorFilter(resources.getColor(R.color.buttonsSettings));

        numberOfComments.setTextColor(resources.getColor(R.color.buttonsSettings));
        numberOfComments.setAlpha(0.6f);

        download.setAlpha(0.6f);
        download.setColorFilter(resources.getColor(R.color.buttonsSettings));

        share.setAlpha(0.6f);
        share.setColorFilter(resources.getColor(R.color.buttonsSettings));

    }
*/

    public static void setSelectedVersion(Version version) {
        selectedVersion = version;
    }

}
