package com.example.laptop.restrict.Adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laptop.restrict.Model.Section;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.SecondLevelExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ThreeLevelListAdapter extends BaseExpandableListAdapter {

    String[] parentHeaders;
    String [] parentHeadersNumber;
    List<List<Section>> secondLevel;
    private Context context;
    List<LinkedHashMap<String, String[]>> data;
    List<LinkedHashMap<String,String[]>> dataNumber;

    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET = {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };

    public ThreeLevelListAdapter(Context context, String[] parentHeadersNumber , String[] parentHeader, List<List<Section>> secondLevel, List<LinkedHashMap<String,String[]>> dataNumber, List<LinkedHashMap<String,String[]>> data) {
        this.context = context;

        this.parentHeaders = parentHeader;
        this.parentHeadersNumber=parentHeadersNumber;

        this.secondLevel = secondLevel;
        this.dataNumber = dataNumber;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return parentHeaders.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {


        // no idea why this code is working

        return 1;

    }

    @Override
    public Object getGroup(int groupPosition) {

        return groupPosition;
    }

    @Override
    public Object getChild(int group, int child) {


        return child;


    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_first, null);

        TextView text = (TextView) convertView.findViewById(R.id.rowParentText);
        TextView adressNo = (TextView)convertView.findViewById(R.id.adressNumber);

        text.setText(this.parentHeaders[groupPosition]);
        adressNo.setText(this.parentHeadersNumber[groupPosition]);

       /* View ind = convertView.findViewById(R.id.arrowChild);
        View ind2 = convertView.findViewById(R.id.arrowChildExpanded);

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ind2.getLayoutParams());
        marginLayoutParams.setMarginEnd(toPxs(16));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginLayoutParams);

        String groupText = getGroup(groupPosition).toString();
        text.setText(groupText);

        if (ind != null) {
            ImageView indicator = (ImageView) ind;
            if (getChildrenCount(groupPosition) == 0) {
                indicator.setVisibility(View.INVISIBLE);
            } else {
                indicator.setVisibility(View.VISIBLE);
                int stateSetIndex = (isExpanded ? 1 : 0);


                if (stateSetIndex == 1) {
                    ind.setVisibility(View.INVISIBLE);
                    ind2.setVisibility(View.VISIBLE);
                    ind2.setLayoutParams(layoutParams);

                    Drawable drawable = indicator.getDrawable();
                    drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
                } else if (stateSetIndex == 0) {
                    ind.setVisibility(View.VISIBLE);
                    //   ind2.setVisibility(View.INVISIBLE);
                    Drawable drawable = indicator.getDrawable();
                    drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
                }
        }    }*/



        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);

        List<Section> headers = secondLevel.get(groupPosition);

        //  Section head = headers.get(groupPosition);


        List<String[]> childData = new ArrayList<>();
        List<String[]> childDataNumber = new ArrayList<>();


        HashMap<String, String[]> secondLevelData=data.get(childPosition);
        HashMap<String, String[]> secondLevelNumber = dataNumber.get(childPosition);





        for(String key : secondLevelData.keySet())
        {


            childData.add(secondLevelData.get(key));

        }

        for(String key2 :secondLevelNumber.keySet())
        {
            childDataNumber.add(secondLevelNumber.get(key2));
        }

        secondLevelELV.setAdapter(new SecondLevelAdapter(context, headers,childData,childDataNumber));

        secondLevelELV.setGroupIndicator(null);


        secondLevelELV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    secondLevelELV.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });


        return secondLevelELV;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

