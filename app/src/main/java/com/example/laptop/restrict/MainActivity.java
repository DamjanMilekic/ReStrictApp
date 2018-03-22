package com.example.laptop.restrict;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.PopUpNotifAdapter;
import com.example.laptop.restrict.Adapter.ThreeLevelListAdapter;
import com.example.laptop.restrict.Fragments.FragmentAppSettingsActivity;
import com.example.laptop.restrict.Fragments.HomeFragment;
import com.example.laptop.restrict.Fragments.LoginFragment;

import com.example.laptop.restrict.Interfaces.ILoginMain;
import com.example.laptop.restrict.Model.DataHome;
import com.example.laptop.restrict.Model.Date;
import com.example.laptop.restrict.Model.DatumPopup;
import com.example.laptop.restrict.Model.NotificationPopup;
import com.example.laptop.restrict.Model.Section;
import com.example.laptop.restrict.RetrofitAppSettings.Client;
import com.example.laptop.restrict.RetrofitAppSettings.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class MainActivity extends AppCompatActivity implements ILoginMain{

    public static final String APP_TOKEN = LoginFragment.api_token;
    android.support.v4.app.Fragment loginFr;

    List<DatumPopup> datumPopups;

    View menuButton;
    Point p;
    Global globalVar;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginFr = new LoginFragment();
        setFragment(loginFr);

        datumPopups = new ArrayList<>();
       globalVar= (Global)getApplicationContext();
//optional show one list at a time
     /*   expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
*/
       // actionBarInit();
      //  findMenuItem();
            findMenuItem();

    }

    private void findMenuItem( )
    {
        final ViewTreeObserver viewTreeObserver = getWindow().getDecorView().getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                menuButton = findViewById(R.id.btnNotificationActBar);
                if (menuButton != null) {


                    int[] location = new int[2];
                    menuButton.getLocationInWindow(location);

                    p = new Point();
                    p.x = location[0];
                    p.y = location[1];


                    globalVar.setPopupPoint(p);
                    if(viewTreeObserver.isAlive()) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    public void setFragment(Fragment frag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.frame) == null) {
           // ft.addToBackStack("login");
            ft.add(R.id.frame,frag);
         //   ft.replace(R.id.famelayout,loginFr);
            ft.commit();
        }

    }
    @Override
    public void onClick() {

        String pathForIntents= "/storage/emulated/0/CHINAFAIRGALLERY/slika1.jpg";
        String imageDirPath="/storage/emulated/0/CHINAFAIRGALLERY";

        Intent i = new Intent(MainActivity.this,PinchZoom.class);
        i.putExtra("bitmapImage",pathForIntents);
        i.putExtra("url",imageDirPath);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().show();
        super.onBackPressed();
    }



}
