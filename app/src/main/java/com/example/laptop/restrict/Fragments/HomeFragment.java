package com.example.laptop.restrict.Fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.laptop.restrict.Adapter.PopUpNotifAdapter;
import com.example.laptop.restrict.Adapter.ThreeLevelListAdapter;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class HomeFragment extends Fragment {


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





    private DisplayMetrics metrics;
    public int width;

    public HomeFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLevel.add(moviesSecLevel);
        secondLevel.add(gamesSecLeve);

        thirdLevelMovies.put(moviesSecLevel[0], chelseaThirdLvl);
        thirdLevelMovies.put(moviesSecLevel[1], chelseaThirdLvl2);
        thirdLevelMovies.put(moviesSecLevel[2], chelseaThirdLvl3);
        thirdLevelDataNumbersMovies.put(moviesSecLevel[0],planNumbersChelsea);
        thirdLevelDataNumbersMovies.put(moviesSecLevel[1],planNumbers2Chelsea);
        thirdLevelDataNumbersMovies.put(moviesSecLevel[2],planNumbers3Chelsea);

        thirdLevelGames.put(gamesSecLeve[0], hoxtonThirdLvl);
        thirdLevelGames.put(gamesSecLeve[1], hoxtonThirdLvl2);
        thirdLevelDataNumbersGames.put(gamesSecLeve[0],planNumbersHoxton);
        thirdLevelDataNumbersGames.put(gamesSecLeve[1],planNumbers2Hoxton);



        data.add(thirdLevelMovies);
        data.add(thirdLevelGames);

        dataNumbers.add(thirdLevelDataNumbersMovies);
        dataNumbers.add(thirdLevelDataNumbersGames);



        expandableListView = (ExpandableListView) view.findViewById(R.id.expandible_listview);
        ThreeLevelListAdapter threeLevelListAdapterAdapter =
                new ThreeLevelListAdapter(getActivity(), parentNumbers,parent, secondLevel,dataNumbers,data);
        expandableListView.setAdapter( threeLevelListAdapterAdapter );
        expandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.expanded_list_indicator));
        expandableListView.setIndicatorBounds(100,100);

        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        expandableListView.setIndicatorBoundsRelative(width-getDipsFromPixel(50),width-getDipsFromPixel(10));


        ((AppCompatActivity)getActivity()).getSupportActionBar().show();


    }

    public int getDipsFromPixel(float pixels)
    {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

}
