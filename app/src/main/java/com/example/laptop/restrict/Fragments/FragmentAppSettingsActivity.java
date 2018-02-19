package com.example.laptop.restrict.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Data;
import com.example.laptop.restrict.Model.Osoba;
import com.example.laptop.restrict.Model.Person;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.RetrofitAppSettings.ApiClientAppSettings;
import com.example.laptop.restrict.RetrofitAppSettings.ApiInterfaceAppSettings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentAppSettingsActivity extends Fragment {

    private FragmentActivity fragmentContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private static final String TAG = "APP";

    public static final String APP_TOKEN = "Q4ramXIqJgnpnQldC6RYmYJmfdvUhmfdjk1hQ9uS1ClH0sTbvXO1Iaslush5";

    EditText name, title, eMail, phone;


    ImageView slikaIme, slikax, slikaRotateLeft, slikaRotateRight, backButton, buttonCheck;
    ImageButton btnNotificationActBar;

    LinearLayout linearLayoutSlicicaIText;

    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentContext = (FragmentActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = fragmentContext.getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_settings, container, false);

        name = (EditText) view.findViewById(R.id.ime);
        title = (EditText) view.findViewById(R.id.title);
        eMail = (EditText) view.findViewById(R.id.eMail);
        phone = (EditText) view.findViewById(R.id.phone);

        slikaIme = (ImageView) view.findViewById(R.id.sl);

        final ImageButton slicicaZaToolbar = (ImageButton)view.findViewById(R.id.btnProfileActBar);
        //Slike za manipulaciju sa slikom


        slikax = (ImageView) view.findViewById(R.id.imagex);
        slikaRotateLeft = (ImageView) view.findViewById(R.id.imagerotateLeft);
        slikaRotateRight = (ImageView) view.findViewById(R.id.imagerotateRight);

        //dugmici za actionBar
        buttonCheck = (ImageView) view.findViewById(R.id.buttonCheck);
        backButton = (ImageView) view.findViewById(R.id.backButton);
        btnNotificationActBar = (ImageButton) view.findViewById(R.id.btnNotificationActBar);


        slikax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slikaIme.setImageResource(R.drawable.backround_circle);
                linearLayoutSlicicaIText.setVisibility(View.VISIBLE);
            }
        });

        slikaRotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slikaIme.setRotation(slikaIme.getRotation() - 90);
            }
        });

        slikaRotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slikaIme.setRotation(slikaIme.getRotation() + 90);
            }
        });


        slikaIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // treba dodati alert dialog
            }
        });


        //dugmici iz actionBar-a
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity = (MainActivity) getActivity();
                mainActivity.onBackPressed();

            }
        });

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dodajOsobu();
            }
        });

        //layout za elemente u kojoj su slicica sa kamerom i mali tekst upload picture
        linearLayoutSlicicaIText = (LinearLayout) view.findViewById(R.id.slicicaItext);

        //dodavanje ako slika  nema content, i ako ima brise placeholder
        if (slikaIme.getDrawable() == null) {
            slikaIme.setImageResource(R.drawable.backround_circle);
            Log.d(TAG, "NO PICTURES");

        } else {
            linearLayoutSlicicaIText.setVisibility(View.GONE);
        }

        //Retrofit postavljanje tekstfildova sa servera
        ApiInterfaceAppSettings apiInterfaceAppSettings =
                ApiClientAppSettings.getApiClient().create(ApiInterfaceAppSettings.class);

        retrofit2.Call<Person> call = apiInterfaceAppSettings.getPerson(APP_TOKEN);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                Data data = response.body().getData();

                name.setText(data.getFirstName());
                title.setText(data.getTitle());
                eMail.setText(data.getEmail());
                phone.setText(data.getProfile().getPhone());

/*
                slikaIme.se(data.getProfile().getImage());
*/
                String urlSlike= "https://s.strictapp.com/" + data.getProfile().getImage();
                Picasso.with(getContext())
                        .load(urlSlike).fit().centerCrop()
                        .into(slikaIme);

                /*Picasso.with(mainActivity.getApplicationContext())
                        .load(urlSlike).fit().centerCrop()
                        .into(slicicaZaToolbar);*/
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.d("Error", "onFailure je pozvan iz enquea" );
                Toast.makeText(getContext(), "onFailure je pozvan iz enquea", Toast.LENGTH_LONG).show();
            }


        });
        return view;
    }


    public void dodajOsobu(){
        String ime = name.getText().toString();
        String titl = title.getText().toString();
        String mail = eMail.getText().toString();
        String tel = phone.getText().toString();



        List<Osoba> osobaList= new ArrayList<>();

        if (ime.trim().length()>0 &&
                titl.trim().length()>0 &&
                mail.trim().length()>0 &&
                tel.trim().length()>0)

            if (isEmailValid(mail)) {
                try {
                    int t = Integer.parseInt(tel);
                    osobaList.add(new Osoba(ime, titl, mail, t));
                    Toast.makeText(getContext(), "Changes made successfully", Toast.LENGTH_LONG).show();

                }catch (Exception e){

                    Toast.makeText(getContext(),"USE ONLY NUMBERS IN PHONE ENTRY", Toast.LENGTH_LONG).show();
                    phone.setError("USE ONLY NUMBERS IN PHONE ENTRY");

                }
            }else{
                Toast.makeText(getContext(),"ENTER EMAIL FORM CORRECTLY", Toast.LENGTH_LONG).show();
                eMail.setError("ENTER EMAIL FORM CORRECTLY");
            }

        else{
            name.setError("ENTER NAME");
            title.setError("ENTER TITLE");
            eMail.setError("ENTER EMAIL");
            phone.setError("ENTER PHONE");
            Toast.makeText(getContext(),"ENTER ALL FIELDS", Toast.LENGTH_LONG).show();

        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
