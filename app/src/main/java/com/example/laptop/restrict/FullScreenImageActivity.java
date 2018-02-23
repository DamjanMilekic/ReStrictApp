package com.example.laptop.restrict;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.laptop.restrict.Adapter.ProjectAdapter;
import com.example.laptop.restrict.Fragments.ImageFragment;
import com.example.laptop.restrict.Model.Version;

public class FullScreenImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    private Version selectedVersion;

    private ImageView backButton;

    @Override
    protected void onStart() {
        super.onStart();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        if(getIntent() != null) {
            selectedVersion = (Version) getIntent().getExtras().getParcelable(ProjectAdapter.SELECTED_VERSION);
            Bundle bundle = new Bundle();
            bundle.putParcelable(ProjectAdapter.SELECTED_VERSION, selectedVersion);
            ImageFragment imageFragment = new ImageFragment();
            imageFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.full_screen_image, imageFragment);
            transaction.commit();
        }

        // Back dugme
        backButton = (ImageView) findViewById(R.id.backButtonFullScreen);

        // Osluskivac za back dugme koje vrsi povratak u DetailActivity
        backButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

}
