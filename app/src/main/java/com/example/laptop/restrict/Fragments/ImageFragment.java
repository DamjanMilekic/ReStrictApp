package com.example.laptop.restrict.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Version;
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

    private Version selectedVersion;

    private ImageView backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Ucitavanje layouta
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_image, container, false);

        image = (ImageView) view.findViewById(R.id.image);

        if (getArguments() != null) {
            selectedVersion = (Version) getArguments().getParcelable(ProjectAdapter.SELECTED_VERSION);

            // Dodavanje slike u ImageView
            Picasso.with(getContext())
                    .load(DetailFragment.STRICTAPP_URL + selectedVersion.getImageFile())
                    .into(image);

            // Klasa koja omogucava zumiranje slike
            photoViewAttacher = new PhotoViewAttacher(image);
            photoViewAttacher.update();

        } else {
            Toast.makeText(getContext(), "Doslo je do greske sa ucitavanjem slike.", Toast.LENGTH_SHORT).show();
        }

        //backDugme u FullscreenImage
        backButton = (ImageView) view.findViewById(R.id.backButtonFullScreen);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onBackPressed();
                Log.d("123", "kliknutBACK: ");
            }
        });


        return view;
    }

}
