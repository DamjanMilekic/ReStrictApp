package com.example.laptop.restrict.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop.restrict.AnimatedExpandableListView;
import com.example.laptop.restrict.Model.Date;
import com.example.laptop.restrict.Model.DrawingHome;
import com.example.laptop.restrict.Model.Section;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.SecondLevelExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.example.laptop.restrict.Fragments.HomeFragment.SECOND_LEVEL_COUNT;

public class ThreeLevelListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter{// BaseExpandableListAdapter {

    List<Date> parentHeader;
    List<List<Section>> secondLevel;
    private Context context;
    ArrayList<List<Section>> data;

    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET = {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };

    public ThreeLevelListAdapter(Context context, List<Date> parentHeader, List<List<Section>> secondLevel, ArrayList<List<Section>> data) {
        this.context = context;
        this.parentHeader = parentHeader;
        this.secondLevel = secondLevel;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return parentHeader.size();
    }

  /*  @Override
    public int getChildrenCount(int groupPosition) {

        // no idea why this code is working

        return 1;

    }*/

    @Override
    public Object getGroup(int groupPosition) {

        return data.get(groupPosition);
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

        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_first, null);

        TextView text = (TextView) convertView.findViewById(R.id.rowParentText);
        TextView adressNo = (TextView)convertView.findViewById(R.id.adressNumber);
        ImageView img = (ImageView)convertView.findViewById(R.id.arrowRght);

        Date date = parentHeader.get(groupPosition);

        text.setText(date.getTitle());
        adressNo.setText(date.getIdentifier());

        if(isExpanded)
        {
            //Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotate_arrow);
          //  img.startAnimation(animation);
            // img.animate().rotationBy(90).setDuration(500).start();
            img.setImageResource(R.drawable.ar_down);
        }
        else
        {
            img.setImageResource(R.drawable.right_arrow);
        }

        secondLevelELV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    secondLevelELV.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        return convertView;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);

        List<Section> headers = secondLevel.get(groupPosition);

        secondLevelELV.setAdapter(new SecondLevelAdapter(context, headers));

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
    public int getRealChildrenCount(int groupPosition) {
        return 1;
    }

  /*  @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);

        List<Section> headers = secondLevel.get(groupPosition);

        secondLevelELV.setAdapter(new SecondLevelAdapter(context, headers));

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
*/
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

