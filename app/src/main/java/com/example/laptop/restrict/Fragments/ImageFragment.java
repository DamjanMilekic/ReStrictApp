package com.example.laptop.restrict.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.laptop.restrict.R;
import com.squareup.picasso.Picasso;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ivandjordjevic on 7.2.18..
 */

public class ImageFragment extends Fragment {

    private ImageView image;
    private PhotoViewAttacher photoViewAttacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_image, container, false);

        image = (ImageView) view.findViewById(R.id.image);

        Picasso.with(getContext())
                .load(R.drawable.technologywallpaper)
                .into(image);

        photoViewAttacher = new PhotoViewAttacher(image);
        photoViewAttacher.update();



        return view;
    }

}
