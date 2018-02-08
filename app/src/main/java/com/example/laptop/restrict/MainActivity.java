package com.example.laptop.restrict;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.media.Image;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop.restrict.Adapter.ThreeLevelListAdapter;
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

    ExpandableListView expandableListView;
    String[] parent = new String[]{"MOVIES", "GAMES"}; // comment this when uncomment bottom
    String[] movies = new String[]{"Horror", "Action", "Thriller/Drama"};
    String[] games = new String[]{"Fps", "Moba", "Rpg", "Racing"};
    String[] horror = new String[]{"Conjuring", "Insidious", "The Ring"};
    String[] action = new String[]{"Jon Wick", "Die Hard", "Fast 7", "Avengers"};
    String[] thriller = new String[]{"Imitation Game", "Tinker, Tailer, Soldier, Spy", "Inception", "Manchester by the Sea"};
    String[] fps = new String[]{"CS: GO", "Team Fortress 2", "Overwatch", "Battlefield 1", "Halo II", "Warframe"};
    String[] moba = new String[]{"Dota 2", "League of Legends", "Smite", "Strife", "Heroes of the Storm"};
    String[] rpg = new String[]{"Witcher III", "Skyrim", "Warcraft", "Mass Effect II", "Diablo", "Dark Souls", "Last of Us"};
    String[] racing = new String[]{"NFS: Most Wanted", "Forza Motorsport 3", "EA: F1 2016", "Project Cars"};

    LinkedHashMap<String, String[]> thirdLevelMovies = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelGames = new LinkedHashMap<>();
    List<String[]> secondLevel = new ArrayList<>();
    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();

    ActionBar mActionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginFr = new LoginFragment();
        //   setFragment(loginFr);

        ActionBarInit();

        secondLevel.add(movies);
        secondLevel.add(games);

        thirdLevelMovies.put(movies[0], horror);
        thirdLevelMovies.put(movies[1], action);
        thirdLevelMovies.put(movies[2], thriller);

        thirdLevelGames.put(games[0], fps);
        thirdLevelGames.put(games[1], moba);
        thirdLevelGames.put(games[2], rpg);
        thirdLevelGames.put(games[3], racing);

        data.add(thirdLevelMovies);
        data.add(thirdLevelGames);

        expandableListView = (ExpandableListView) findViewById(R.id.expandible_listview);
        ThreeLevelListAdapter threeLevelListAdapterAdapter = new ThreeLevelListAdapter(this, parent, secondLevel, data);
        expandableListView.setAdapter(threeLevelListAdapterAdapter);

        //Image view za app settings
        ImageView iv = (ImageView) findViewById(R.id.btnProfileActBar);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               initFragment();
               getSupportActionBar().hide();
                /*Intent i = new Intent(MainActivity.this, AppSettingsActivity.class);
                startActivity(i);*/
            }
        });



        //Image view za app settings
        ImageView iv = (ImageView) findViewById(R.id.btnProfileActBar);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AppSettingsActivity.class);
                startActivity(i);
            }
        });

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
    }

    public void setFragment(Fragment frag)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.famelayout) == null) {
            ft.replace(R.id.famelayout,loginFr);
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
