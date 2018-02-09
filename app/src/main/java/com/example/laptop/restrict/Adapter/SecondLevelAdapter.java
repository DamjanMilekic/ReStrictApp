package com.example.laptop.restrict.Adapter;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.Fragments.LoginFragment;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.R;

import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.concurrent.RecursiveTask;


public class SecondLevelAdapter extends BaseExpandableListAdapter {

    private Context context;

    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET = { android.R.attr.state_expanded };
    private static final int[][] GROUP_STATE_SETS = { EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };
    LoginFragment loginFr;
    List<String[]> dataNumbers;
    List<String[]> data;

   String[] headers;


    public SecondLevelAdapter(Context context, String[] headers,List<String[]> data,List<String[]> dataNumbers) {
        this.context = context;

        this.data = data;
        this.dataNumbers=dataNumbers;
        this.headers = headers;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return headers[groupPosition];
    }

    @Override
    public int getGroupCount() {

        return headers.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_second, null);
        TextView text = (TextView) convertView.findViewById(R.id.rowSecondText);
        View ind = convertView.findViewById(R.id.arrowChild);
        View ind2 = convertView.findViewById(R.id.arrowChildExpanded);
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
                    Drawable drawable = indicator.getDrawable();
                    drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
                } else if (stateSetIndex == 0) {
                    ind.setVisibility(View.VISIBLE);
                    ind2.setVisibility(View.INVISIBLE);
                    Drawable drawable = indicator.getDrawable();
                    drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
                }
            }


        }
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

    /*    String[] childData;
        String [] numberData;

        childData = data.get(groupPosition);
        //numberData = dataNumbers.get(groupPosition);*/

       // return childData[childPosition];
        return childPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_third, null);

        TextView textView = (TextView) convertView.findViewById(R.id.rowThirdText);
        TextView basePlanNumber = (TextView)convertView.findViewById(R.id.basePlanNumber);

        String[] childArray=data.get(groupPosition);
        String[] childNumber = dataNumbers.get(groupPosition);

        String text = childArray[childPosition];
        String number = childNumber[childPosition];

        textView.setText(text);
        basePlanNumber.setText(number);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                Toast.makeText(context, "Ovde vezati detalje", Toast.LENGTH_SHORT).show();
            }
        });



        return convertView;
    }

    public void setFragment(Fragment frag)
    {
        FragmentManager fm =((Activity)context).getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.famelayout,frag);
            ft.addToBackStack("login");
            ft.commit();


    }

    public interface IThirdLevelClick{
        public void onThirdLevelClick(View view,int position);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] children = data.get(groupPosition);


        return children.length;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
