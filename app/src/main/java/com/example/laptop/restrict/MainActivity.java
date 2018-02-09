package com.example.laptop.restrict;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.PopUpNotifAdapter;
import com.example.laptop.restrict.Adapter.ThreeLevelListAdapter;
import com.example.laptop.restrict.Fragments.DetailFragment;
import com.example.laptop.restrict.Fragments.FragmentAppSettingsActivity;
import com.example.laptop.restrict.Fragments.LoginFragment;
import com.example.laptop.restrict.Interfaces.ILoginMain;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public  class MainActivity extends AppCompatActivity implements ILoginMain {


  Fragment loginFr;

    Point p;
    View menuButton;

    ExpandableListView expandableListView;
    String[] parent = new String[]{"Chelsea Barracks", "Hoxton"};
    String [] parentNumbers = new String[]{"9472","4493"};
    //Floors
    String[] moviesSecLevel = new String[]{"Floor 1", "Floor 2", "Floor 3"};
    String[] gamesSecLeve = new String[]{"Floor 1", "Floor 2"};


    //Chelsea
    String[] chelseaThirdLvl = new String[]{"chelsea1 base", "chelsea 2 base"};
    String[] chelseaThirdLvl2 = new String[]{"Floor 2 chelsea 2 base"};
    String[] chelseaThirdLvl3 = new String[]{"Floor 3 chelsea 3 base", "Floor 3 chelsea3 base", "Floor 3 chelsea 3 base"};

    String[] planNumbersChelsea = new String[]{"280","281"};
    String[] planNumbers2Chelsea = new String[]{"180"};

    String[] planNumbers3Chelsea = new String[]{"380","381","382"};
    //Hoxton
    String[] hoxtonThirdLvl = new String[]{"Ground floor 1 Hoxton ", "Ground floor 2 Hoxton"};
    String[] hoxtonThirdLvl2 = new String[]{"Ground floor 2 Hoxton"};

    String[] planNumbersHoxton = new String[]{"008","009"};
    String[] planNumbers2Hoxton = new String[]{"118a"};






    LinkedHashMap<String, String[]> thirdLevelMovies = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelGames = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelDataNumbersMovies = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelDataNumbersGames = new LinkedHashMap<>();


    List<String[]> secondLevel = new ArrayList<>();
    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();
    List<LinkedHashMap<String, String[]>> dataNumbers = new ArrayList<>();

    List<String> notificationList;


    ActionBar mActionBar;
    private DisplayMetrics metrics;
    public int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationList = new ArrayList<>();

        notificationList.add("Lorem ipsum dolor sit amet,consaectetur..");
        notificationList.add("Test notifictation");
        notificationList.add("Test notification 2");



         findMenuItem();
          actionBarInit();

        loginFr = new LoginFragment();
        setFragment(loginFr);
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
    }

    private void initFragment2(){

        DetailFragment detailFragment = new DetailFragment();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

       fragmentTransaction.setCustomAnimations(R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down, R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.add(R.id.frame, detailFragment).commit();

    }

    private void actionBarInit()

     private void initFragment(){

         FragmentAppSettingsActivity fragmentAppSettingsActivity = new FragmentAppSettingsActivity();

         android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

         android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

         fragmentTransaction.setCustomAnimations(R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down, R.anim.slide_from_down_to_up, R.anim.slide_from_up_to_down);
         fragmentTransaction.addToBackStack(null);

         fragmentTransaction.add(R.id.frame, fragmentAppSettingsActivity).commit();


    }


    private void ActionBarInit()
    {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.toolbar_layout, null);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        ImageButton notification = (ImageButton)mCustomView.findViewById(R.id.btnNotificationActBar);
        ImageButton imgProfile = (ImageButton)mCustomView.findViewById(R.id.btnProfileActBar);
        TextView numberOfNotif = (TextView)mCustomView.findViewById(R.id.txNumberOfNotif);

        numberOfNotif.setText(String.valueOf(notificationList.size()));



        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
















                Toast.makeText(MainActivity.this, "Ovde vezati AppSetting", Toast.LENGTH_SHORT).show();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               showPopUp(MainActivity.this,p,v);
            }
        });
    }

    public void setFragment(Fragment frag)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.famelayout) == null) {
           // ft.addToBackStack("login");
            ft.add(R.id.famelayout,frag);
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

    private void findMenuItem()
    {
        final ViewTreeObserver viewTreeObserver = getWindow().getDecorView().getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                menuButton = findViewById(R.id.btnNotificationActBar);
                if (menuButton != null) {
                    // Found it! Do what you need with the button
                    int[] location = new int[2];
                    menuButton.getLocationInWindow(location);

                    p = new Point();
                    p.x = location[0];
                    p.y = location[1];

                    if(viewTreeObserver.isAlive()) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    private void showPopUp(final Activity context,Point p,View view)
    {
       /* int popupWidth = 400;
        int popupHeight = 200;

        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_notifications, viewGroup);

        final PopupWindow popup = new PopupWindow(layout);
        popup.setContentView(layout);
        popup.setWidth(width);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());

        int OFFSET_X = 5;
        int OFFSET_Y = 90;


        popup.showAtLocation(view, Gravity.NO_GRAVITY, p.x+OFFSET_X, p.y+OFFSET_Y );
*/

        View popupView = getLayoutInflater().inflate(R.layout.popup_notifications, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());


        int location[] = new int[2];
        int OFFSET_X = 0;
        int OFFSET_Y = 90;

       // view.getLocationOnScreen(location);

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, p.x+OFFSET_X, p.y+OFFSET_Y );


        RecyclerView recyclerView = (RecyclerView)popupView.findViewById(R.id.rvNotify);
        LinearLayoutManager vertical
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(vertical);

        PopUpNotifAdapter notifAdapter = new PopUpNotifAdapter(this,notificationList);
        recyclerView.setAdapter(notifAdapter);







    }
    @Override
    public void onBackPressed() {
        getSupportActionBar().show();


        super.onBackPressed();
    }



}
