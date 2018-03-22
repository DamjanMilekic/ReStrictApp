package com.example.laptop.restrict.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.laptop.restrict.Adapter.PopUpNotifAdapter;
import com.example.laptop.restrict.Adapter.ThreeLevelListAdapter;

import com.example.laptop.restrict.Global;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.DataHome;
import com.example.laptop.restrict.Model.Date;
import com.example.laptop.restrict.Model.DatumPopup;
import com.example.laptop.restrict.Model.DrawingHome;
import com.example.laptop.restrict.Model.NotificationPopup;
import com.example.laptop.restrict.Model.Section;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.RetrofitAppSettings.Client;
import com.example.laptop.restrict.RetrofitAppSettings.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment  {


    ExpandableListView expandableListView;
    Point p;
    View menuButton;

    public Context context;
    PopUpNotifAdapter notifAdapter;
    ActionBar mActionBar;
    List<List<Section>> secondLevel = new ArrayList<List<Section>>();

    List<DatumPopup> notifList;
    private DisplayMetrics metrics;
    public int width;
    Global globalVar;

    TextView numberOfNotif;
    ImageButton imgProfile;
    ImageButton notification;

    public HomeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)

        setHasOptionsMenu(true);

        notifList = new ArrayList<>();
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       /* if(getActivity().getIntent().getParcelableArrayListExtra("notif").size()>0)
        {
            notifList = getActivity().getIntent().getParcelableArrayListExtra("notif");
        }*/
        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandible_listview);

        getProjects();

        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        expandableListView.setIndicatorBoundsRelative(width-getDipsFromPixel(0),width-getDipsFromPixel(5));

        actionBarInit();


    }

    private void getNotification(final View view) {
        notification.setEnabled(false);
        Service apiService = Client.getApiClient().create(Service.class);
        Call<NotificationPopup> call = apiService.getNotificationsPopup(LoginFragment.api_token);
        globalVar = (Global) getActivity().getApplicationContext();
        call.enqueue(new Callback<NotificationPopup>() {
            @Override
            public void onResponse(Call<NotificationPopup> call, Response<NotificationPopup> response) {

                if (response.body() != null)
                {
                    NotificationPopup notificationPopup = response.body();
                    notifList = notificationPopup.getData();



                    p = globalVar.getPopupPoint();
                    showPopUp(getActivity(),p,view,notifList);
                    notifAdapter.notifyDataSetChanged();

                    notification.setEnabled(true);
                }


            }

            @Override
            public void onFailure(Call<NotificationPopup> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail on server", Toast.LENGTH_LONG).show();

            }
        });


    }
   public void actionBarInit()
   {
       mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
       mActionBar.setDisplayShowHomeEnabled(false);
       mActionBar.setDisplayShowTitleEnabled(false);
       LayoutInflater mInflater = LayoutInflater.from(getActivity());

       View mCustomView = mInflater.inflate(R.layout.toolbar_layout, null);

       mActionBar.setCustomView(mCustomView);
       mActionBar.setDisplayShowCustomEnabled(true);

      notification = mCustomView.findViewById(R.id.btnNotificationActBar);
       imgProfile = mCustomView.findViewById(R.id.btnProfileActBar);
       numberOfNotif = mCustomView.findViewById(R.id.txNumberOfNotif);




       imgProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


           //    initFragmentAppSettings();
          //     mActionBar.hide();

           }
       });
       notification.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(notifList.size()>0)
               {
                   showPopUp(getActivity(),p,v,notifList);
               }
               else {
                   getNotification(v);
               }



           }
       });

       mActionBar.show();
   }


    private void showPopUp(Context context, Point p, View view, List<DatumPopup> notifList)
    {

        View popupView = getLayoutInflater().inflate(R.layout.popup_notifications, null);
        RecyclerView recyclerView = (RecyclerView)popupView.findViewById(R.id.rvNotify);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(null);


          int location[] = new int[2];
        int OFFSET_X = 0;
        int OFFSET_Y = -40;

        // view.getLocationOnScreen(location);


        notifAdapter = new PopUpNotifAdapter(getActivity(),notifList);
        recyclerView.setAdapter(notifAdapter);
        LinearLayoutManager vertical
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(vertical);



        popupWindow.setAnimationStyle(R.style.popup_scale_animation);
        popupWindow.showAsDropDown(view, p.x+OFFSET_X, p.y+OFFSET_Y );

    }
    private void getProjects() {

        Service apiService = Client.getApiClient().create(Service.class);
        Call<DataHome> call = apiService.getProjects(LoginFragment.api_token);

        call.enqueue(new Callback<DataHome>() {
            @Override
            public void onResponse(Call<DataHome> call, Response<DataHome> response) {

                if (response.body() != null)
                {
                    DataHome home = response.body();

                    List<Date> dateList = home.getData();
                    ArrayList<List<Section>> sectionLists = new ArrayList<>();
                    List<Section> subSectionLists =new ArrayList<>();

                    for (Date dt:dateList) {

                        sectionLists.add(dt.getSections());


                        for (List<Section> sections :sectionLists) {

                            subSectionLists = sections;

                        }

                        secondLevel.add(subSectionLists);

                    }

                    ThreeLevelListAdapter threeLevelListAdapterAdapter =
                            new ThreeLevelListAdapter(getActivity(), dateList, secondLevel,sectionLists);
                    expandableListView.setAdapter( threeLevelListAdapterAdapter );

                }

            }

            @Override
            public void onFailure(Call<DataHome> call, Throwable t) {
                Toast.makeText(getContext(), "Fail on server", Toast.LENGTH_LONG).show();

            }
        });
    }
    public int getDipsFromPixel(float pixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }



}
