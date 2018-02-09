package com.example.laptop.restrict.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

// Fragment za prikaz slike
public class ImageFragment extends Fragment {

    private ImageView image;
    private PhotoViewAttacher photoViewAttacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Ucitavanje layouta
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_image, container, false);

        image = (ImageView) view.findViewById(R.id.image);

        // Dodavanje slike u ImageView
        Picasso.with(getContext())
                .load(R.drawable.landscape)
                .into(image);

        // Klasa koja omogucava zumiranje slike
        photoViewAttacher = new PhotoViewAttacher(image);
        photoViewAttacher.update();

        return view;
    }

}
