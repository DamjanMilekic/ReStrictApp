package com.example.laptop.restrict.Fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.ThreeLevelListAdapter;
import com.example.laptop.restrict.Model.DataHome;
import com.example.laptop.restrict.Model.Date;
import com.example.laptop.restrict.Model.DrawingHome;
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


public class HomeFragment extends Fragment {

    ArrayList<Date> projects;

    Point p;
    View menuButton;

    ExpandableListView expandableListView;


    String[] parent = new String[]{"Chelsea Barracks", "Hoxton"};
    String [] parentNumbers = new String[]{"9472","4493"};

    String[] moviesSecLevel = new String[]{"Floor 1", "Floor 2", "Floor 3"};

    //  String[] planNumbersChelsea = new String[]{"280","281"};
    // String[] chelseaThirdLvl = new String[]{"chelsea1 base", "chelsea 2 base"};









    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();
    List<LinkedHashMap<String, String[]>> dataNumbers = new ArrayList<>();

    List<List<Section>> secondLevel = new ArrayList<List<Section>>();

    LinkedHashMap<String, String[]> thirdLevelMovies = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelDataNumbersMovies = new LinkedHashMap<>();


    String[] parentMock,parentMockNumbers,secondMock,thirdMock,thirdMockNumbers;


    //  String[] gamesSecLeve = new String[]{"Floor 1", "Floor 2"};



    // String[] chelseaThirdLvl2 = new String[]{"Floor 2 chelsea 2 base"};
    // String[] chelseaThirdLvl3 = new String[]{"Floor 3 chelsea 3 base", "Floor 3 chelsea3 base", "Floor 3 chelsea 3 base"};



    //  String[] planNumbers2Chelsea = new String[]{"180"};
    //  String[] planNumbers3Chelsea = new String[]{"380","381","382"};
    //Hoxton
    //  String[] hoxtonThirdLvl = new String[]{"Ground floor 1 Hoxton ", "Ground floor 2 Hoxton"};
    //  String[] hoxtonThirdLvl2 = new String[]{"Ground floor 2 Hoxton"};

    //  String[] planNumbersHoxton = new String[]{"008","009"};
    //  String[] planNumbers2Hoxton = new String[]{"118a"};






    //  LinkedHashMap<String, String[]> thirdLevelGames = new LinkedHashMap<>();
    //  LinkedHashMap<String, String[]> thirdLevelDataNumbersGames = new LinkedHashMap<>();








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





        //  thirdLevelMovies.put(moviesSecLevel[1], chelseaThirdLvl2);
        //thirdLevelMovies.put(moviesSecLevel[2], chelseaThirdLvl3);



        // thirdLevelDataNumbersMovies.put(moviesSecLevel[1],planNumbers2Chelsea);
        //  thirdLevelDataNumbersMovies.put(moviesSecLevel[2],planNumbers3Chelsea);

        //thirdLevelGames.put(gamesSecLeve[0], hoxtonThirdLvl);
        //thirdLevelGames.put(gamesSecLeve[1], hoxtonThirdLvl2);
        //thirdLevelDataNumbersGames.put(gamesSecLeve[0],planNumbersHoxton);
        // thirdLevelDataNumbersGames.put(gamesSecLeve[1],planNumbers2Hoxton);




        //data.add(thirdLevelGames);

        //  dataNumbers.add(thirdLevelDataNumbersGames);



        expandableListView = (ExpandableListView) view.findViewById(R.id.expandible_listview);
        getProjects();
        //  expandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.expanded_list_indicator));

        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        expandableListView.setIndicatorBoundsRelative(width-getDipsFromPixel(0),width-getDipsFromPixel(5));



        ((AppCompatActivity)getActivity()).getSupportActionBar().show();


    }

    private void getProjects()
    {



        Service apiService = Client.getApiClient().create(Service.class);
        Call<DataHome> call = apiService.getProjects(LoginFragment.api_token);

        call.enqueue(new Callback<DataHome>() {
            @Override
            public void onResponse(Call<DataHome> call, Response<DataHome> response) {

                if (response.body() != null)
                {
                    DataHome home = response.body();

                    //FirstLevel
                    List<Date> dateList = home.getData();
                    ArrayList<String> title = new ArrayList<>();
                    ArrayList<String> identifier = new ArrayList<>();
                    ArrayList<String> secLevelTitle = new ArrayList<>();
                    ArrayList<String> thirdLevelTitle = new ArrayList<>();
                    ArrayList<String> thirdLevelIdentifier = new ArrayList<>();

                    ArrayList<List<Section>> sectionLists = new ArrayList<>();
                    List<Section> subSectionLists =new ArrayList<>();

                    ArrayList<List<DrawingHome>> drawingList = new ArrayList<>();
                    List<DrawingHome> subDrawingList = new ArrayList<>();


                    for (Date dt:dateList) {

                        title.add(dt.getTitle());
                        identifier.add(dt.getIdentifier());
                        parentMock = title.toArray(new String[0]) ;
                        parentMockNumbers= identifier.toArray(new String[0]);

                        sectionLists.add(dt.getSections());


                        for (List<Section> sections :sectionLists) {

                            subSectionLists = sections;

                        }
                        for(Section s:subSectionLists)
                        {

                            secLevelTitle.add(s.getTitle());
                            secondMock = secLevelTitle.toArray(new String[0]);

                            drawingList.add(s.getDrawings());
                            for(List<DrawingHome> d:drawingList)
                            {
                                subDrawingList = d;
                            }
                            for(DrawingHome drawingHome:subDrawingList)
                            {
                                thirdLevelTitle.add(drawingHome.getTitle());
                                thirdLevelIdentifier.add(drawingHome.getIdentifier());

                                thirdMock = thirdLevelTitle.toArray(new String[0]);
                                thirdMockNumbers = thirdLevelIdentifier.toArray(new String[0]);

                            }

                            thirdLevelMovies.put(secondMock[0],thirdMock);
                            thirdLevelDataNumbersMovies.put(secondMock[0],thirdMockNumbers);


                            secondLevel.add(subSectionLists);

                            secLevelTitle.clear();
                            secondMock=null;

                            thirdLevelTitle.clear();
                            thirdLevelIdentifier.clear();


                            thirdMock=null;
                            thirdMockNumbers=null;
                        }



                    }




                    //   secondLevel.add(secondMock);
                    //secondLevel.add(gamesSecLeve);



                    data.add(thirdLevelMovies);

                    dataNumbers.add(thirdLevelDataNumbersMovies);


                    ThreeLevelListAdapter threeLevelListAdapterAdapter =
                            new ThreeLevelListAdapter(getActivity(), parentMockNumbers,parentMock, secondLevel,dataNumbers,data);
                    expandableListView.setAdapter( threeLevelListAdapterAdapter );

                }

                // Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DataHome> call, Throwable t) {
                Toast.makeText(getContext(), "Fail on server", Toast.LENGTH_LONG).show();

            }
        });
    }

    public int getDipsFromPixel(float pixels)
    {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

}
