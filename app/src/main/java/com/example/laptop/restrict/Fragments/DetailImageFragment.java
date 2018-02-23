package com.example.laptop.restrict.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.DetailActivity;
import com.example.laptop.restrict.FullScreenImageActivity;
import com.example.laptop.restrict.Model.Version;

import com.example.laptop.restrict.R;
import com.squareup.picasso.Picasso;


public class DetailImageFragment extends Fragment {

    private ImageView image, viewOnFullScreen, un_lock;
    private Version selectedVersion;

    public DetailImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_image, container, false);

        image = (ImageView) view.findViewById(R.id.image);

        // Ucitavanje slike iz objekta koji je poslat iz DetailActivity-a
        if (getArguments() != null) {
            selectedVersion = (Version) getArguments().getParcelable(ProjectAdapter.SELECTED_VERSION);

            // Dodavanje slike u ImageView
            Picasso.with(getContext())
                    .load(DetailActivity.STRICTAPP_URL + selectedVersion.getImageFile())
                    .into(image);

        }

        viewOnFullScreen = (ImageView) view.findViewById(R.id.imgResizeFront);
        viewOnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOnFullScreen();
            }
        });

        un_lock = (ImageView) view.findViewById(R.id.imgLockFront);
        un_lock.setOnClickListener(new View.OnClickListener() {

            int currentResource = R.mipmap.padunlock;

            @Override
            public void onClick(View v) {
                switch(currentResource) {
                    case R.mipmap.padunlock:
                        un_lock.setImageResource(R.mipmap.padlock);
                        currentResource = R.mipmap.padlock;
                        break;
                    case R.mipmap.padlock:
                        un_lock.setImageResource(R.mipmap.padunlock);
                        currentResource = R.mipmap.padunlock;
                        break;
                }
            }

        });

        return view;
    }

    private void viewOnFullScreen() {
        Intent intent = new Intent(getContext(), FullScreenImageActivity.class);
        intent.putExtra(ProjectAdapter.SELECTED_VERSION, selectedVersion);
        startActivity(intent);

    }
}
