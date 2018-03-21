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


    ExpandableListView expandableListView;

    List<List<Section>> secondLevel = new ArrayList<List<Section>>();

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



        expandableListView = (ExpandableListView) view.findViewById(R.id.expandible_listview);
        getProjects();

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

                   // expandableListView.setPadding(0,getDipsFromPixel(10),0,getDipsFromPixel(10));
                    expandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.expanded_list_indicator));
                    metrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    width = metrics.widthPixels;
                    expandableListView.setIndicatorBoundsRelative(width-getDipsFromPixel(39),width-getDipsFromPixel(1));
                }

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