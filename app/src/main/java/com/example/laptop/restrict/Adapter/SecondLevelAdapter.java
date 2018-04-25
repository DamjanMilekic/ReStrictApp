package com.example.laptop.restrict.Adapter;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.AnimatedExpandableListView;
import com.example.laptop.restrict.DetailActivity;
import com.example.laptop.restrict.Fragments.DetailFragment;
import com.example.laptop.restrict.Fragments.InfoFragment;
import com.example.laptop.restrict.Fragments.LoginFragment;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Drawing;
import com.example.laptop.restrict.Model.DrawingHome;
import com.example.laptop.restrict.Model.Section;
import com.example.laptop.restrict.R;

import java.util.List;


public class SecondLevelAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter { // BaseExpandableListAdapter {

    private Context context;

    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET = {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };
    LoginFragment loginFr;
    List<Section> headers;

    public SecondLevelAdapter(Context context, List<Section> headers){//, List<String[]> dataNumbers) {
        this.context = context;
        this.headers = headers;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        return headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        final Section section = headers.get(groupPosition);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_second, null);
        TextView text = (TextView) convertView.findViewById(R.id.rowSecondText);
        ImageView img = convertView.findViewById(R.id.arrowRght);

        text.setText(section.getTitle());

        if(isExpanded)
        {
            img.setImageResource(R.drawable.arrow_down);

        }
        else
        {

            //   img.startAnimation(AnimationUtils.loadAnimation(context,R.anim.rotate_arrow));
            img.setImageResource(R.drawable.arrow_right);

        }
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

         return childPosition;

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_third, null);

        TextView textView = (TextView) convertView.findViewById(R.id.rowThirdText);
        TextView basePlanNumber = (TextView) convertView.findViewById(R.id.basePlanNumber);


        final Section child = headers.get(groupPosition);
        List<DrawingHome> childDrawings = child.getDrawings();

        final DrawingHome drawing = childDrawings.get(childPosition);
        textView.setText(drawing.getTitle());
        basePlanNumber.setText(drawing.getIdentifier());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initFragment2();
                sendDrawing(drawing);
                // Toast.makeText(context, "Ovde vezati detalje", Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        Section child = headers.get(groupPosition);
        List<DrawingHome> childDrawings = child.getDrawings();

        return childDrawings.size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void setFragment(Fragment frag) {
        FragmentManager fm = ((Activity) context).getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame, frag);
        ft.addToBackStack("login");
        ft.commit();


    }

    public interface IThirdLevelClick {
        public void onThirdLevelClick(View view, int position);
    }
    private void initFragment2() {

        /*DetailFragment detailFragment = new DetailFragment();

        AppCompatActivity activity = (AppCompatActivity) context;

        android.support.v4.app.FragmentManager fragmentManager = activity.getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_out_left, R.anim.slide_from_right,R.anim.slide_out_left);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.add(R.id.frame, detailFragment).commit();*/
        Intent intent = new Intent(context, DetailActivity.class);
        ((MainActivity) context).startActivity(intent);

    }

    private void sendDrawing(DrawingHome drawing) {

        if (drawing.getVersions().size() > 0) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("drawing", drawing);
            intent.putExtra("drawing_id", drawing.getId());
            intent.putExtra("drawing_title", drawing.getTitle());
            context.startActivity(intent);
        }

    }

}
