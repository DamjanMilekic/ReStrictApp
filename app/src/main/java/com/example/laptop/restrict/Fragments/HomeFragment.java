package com.example.laptop.restrict.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.laptop.restrict.Adapter.PopUpNotifAdapter;
import com.example.laptop.restrict.Adapter.ThreeLevelListAdapter;

import com.example.laptop.restrict.AnimatedExpandableListView;
import com.example.laptop.restrict.DetailActivity;
import com.example.laptop.restrict.Global;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Comment;
import com.example.laptop.restrict.Model.DataHome;
import com.example.laptop.restrict.Model.Date;
import com.example.laptop.restrict.Model.DatumPopup;
import com.example.laptop.restrict.Model.DrawingHome;
import com.example.laptop.restrict.Model.NotificationPopup;
import com.example.laptop.restrict.Model.Section;
import com.example.laptop.restrict.Model.TypePopup;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.RetrofitAppSettings.Client;
import com.example.laptop.restrict.RetrofitAppSettings.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements PopUpNotifAdapter.PopupItemClickListener{



    Point p;
    View menuButton;

    public Context context;
    PopUpNotifAdapter notifAdapter;
     PopUpNotifAdapter.PopupItemClickListener subscribedMovieItemClickListener;

    ActionBar mActionBar;
    List<List<Section>> secondLevel = new ArrayList<List<Section>>();

    List<DatumPopup> notifList;
    private DisplayMetrics metrics;
    public int width;
    Global globalVar;
    private boolean isShowned=false;

    TextView numberOfNotif;
    ImageButton imgProfile;
    ImageButton notification;

    public static int actionBarHeight=0;


    public static final int FIRST_LEVEL_COUNT = 6;
    public static final int SECOND_LEVEL_COUNT = 4;
    public static final int THIRD_LEVEL_COUNT = 5;
    private AnimatedExpandableListView expandableListView;



    public HomeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)

            setHasOptionsMenu(true);

        notifList = new ArrayList<>();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


            expandableListView = (AnimatedExpandableListView)view.findViewById(R.id.mainList);


        getProjects();


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    expandableListView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });

        actionBarInit(view);

        getActionBarHeight();

    }

    public void initFragmentAppSettings(){

        FragmentAppSettingsActivity fragmentAppSettingsActivity = new FragmentAppSettingsActivity();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down, R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down);
        fragmentTransaction.addToBackStack("appsettings");

        fragmentTransaction.replace(R.id.frame, fragmentAppSettingsActivity,"appsettings").commit();



    }
    public void actionBarInit(View view) {
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());

        View mCustomView = mInflater.inflate(R.layout.toolbar_layout, null);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        notification = mCustomView.findViewById(R.id.btnNotificationActBar);
        imgProfile = mCustomView.findViewById(R.id.btnProfileActBar);
        numberOfNotif = mCustomView.findViewById(R.id.txNumberOfNotif);

        if(notifList.size()==0)
        {
            getNotification(view);
        }



        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    initFragmentAppSettings();
                     mActionBar.hide();

            }
        });
  
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




             //  notification.setEnabled(false);
                if (notifList.size() > 0) {
                    showPopUp(getActivity(), p, v, notifList);


                } else {


                    getNotification(v);

                }
               // notification.setEnabled(true);


            }
        });

        if (notifList.size() > 0) {
            numberOfNotif.setText(Integer.toString(notifList.size()));
        }
        mActionBar.show();

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
        notifAdapter = new PopUpNotifAdapter(getActivity(), notifList);
        notifAdapter.setClickListener(this);
        recyclerView.setAdapter(notifAdapter);

        LinearLayoutManager vertical
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(vertical);
        final ViewGroup root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();

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
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
    }

    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, actionBarHeight, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }
    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    private void getProjects() {

        Service apiService = Client.getApiClient().create(Service.class);
        Call<DataHome> call = apiService.getProjects(LoginFragment.api_token);

        call.enqueue(new Callback<DataHome>() {
            @Override
            public void onResponse(Call<DataHome> call, Response<DataHome> response) {

                if (response.body() != null) {
                    DataHome home = response.body();

                    List<Date> dateList = home.getData();
                    ArrayList<List<Section>> sectionLists = new ArrayList<>();
                    List<Section> subSectionLists = new ArrayList<>();

                    for (Date dt : dateList) {

                        sectionLists.add(dt.getSections());


                        for (List<Section> sections : sectionLists) {

                            subSectionLists = sections;

                        }

                        secondLevel.add(subSectionLists);

                    }

                    ThreeLevelListAdapter threeLevelListAdapterAdapter =
                            new ThreeLevelListAdapter(getActivity(), dateList, secondLevel, sectionLists);
                    expandableListView.setAdapter(threeLevelListAdapterAdapter);

                    // expandableListView.setPadding(0,getDipsFromPixel(10),0,getDipsFromPixel(10));
                  //  expandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.expanded_list_indicator));
                    metrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    width = metrics.widthPixels;
                   // expandableListView.setIndicatorBoundsRelative(width - getDipsFromPixel(39), width - getDipsFromPixel(1));
                }

            }

            @Override
            public void onFailure(Call<DataHome> call, Throwable t) {
                Toast.makeText(getContext(), "Fail on server", Toast.LENGTH_LONG).show();

            }
        });
    }
    private void getNotification(final View view) {

        Service apiService = Client.getApiClient().create(Service.class);
        Call<NotificationPopup> call = apiService.getNotificationsPopup(LoginFragment.api_token);
        globalVar = (Global) getActivity().getApplicationContext();
        call.enqueue(new Callback<NotificationPopup>() {

            @Override
            public void onResponse(Call<NotificationPopup> call, Response<NotificationPopup> response) {

                if (response.body() != null) {
                    NotificationPopup notificationPopup = response.body();
                    notifList = notificationPopup.getData();


                    p = globalVar.getPopupPoint();

                    numberOfNotif.setText(String.valueOf(notifList.size()));
                }


            }

            @Override
            public void onFailure(Call<NotificationPopup> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail on server", Toast.LENGTH_LONG).show();

            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();


    }


  @Override
    public void onPopClick(View view, int position) {

        DatumPopup notification = notifList.get(position);


        if(Integer.valueOf(notification.getTypeId()) == 1 )
        {
            Comment comment = notification.getComment();
            //ovde treba dodati metodu koja vodi do tog komentara na stranici detalji
            Toast.makeText(getActivity(), "Ovo vodi na komentar", Toast.LENGTH_SHORT).show();
        }
        else if(Integer.valueOf(notification.getTypeId()) == 2 )
        {
            TypePopup typeNotif = notification.getType();
            Toast.makeText(getActivity(), "Ovo vodi na crtez", Toast.LENGTH_SHORT).show();
            //a ovde do crteza
        }

    }




}

