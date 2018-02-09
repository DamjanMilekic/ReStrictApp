package com.example.laptop.restrict.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Osoba;
import com.example.laptop.restrict.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentAppSettingsActivity extends Fragment {

    private FragmentActivity fragmentContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private static final String TAG = "APP";


    EditText name, title, eMail, phone;
    Button test;


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

        name = (EditText)view.findViewById(R.id.ime);
        title = (EditText)view. findViewById(R.id.title);
        eMail = (EditText)view. findViewById(R.id.eMail);
        phone = (EditText)view. findViewById(R.id.phone);

        slikaIme = (ImageView)view. findViewById(R.id.sl);

        //Slike za manipulaciju sa slikom

        slikax = (ImageView)view. findViewById(R.id.imagex);
        slikaRotateLeft = (ImageView)view. findViewById(R.id.imagerotateLeft);
        slikaRotateRight = (ImageView)view. findViewById(R.id.imagerotateRight);

        //dugmici za actionBar
        buttonCheck = (ImageView)view.findViewById(R.id.buttonCheck);
        backButton = (ImageView)view.findViewById(R.id.backButton);
        btnNotificationActBar = (ImageButton)view.findViewById(R.id.btnNotificationActBar);



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
                mainActivity = (MainActivity)getActivity();
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
        linearLayoutSlicicaIText = (LinearLayout)view. findViewById(R.id.slicicaItext);

        //dodavanje ako slika  nema content, i ako ima brise placeholder
        if (slikaIme.getDrawable()==null){
            slikaIme.setImageResource(R.drawable.backround_circle);
            Log.d(TAG, "nema slike");

        }
        else {
            linearLayoutSlicicaIText.setVisibility(View.GONE);
        }


/*
        mainActivity = (MainActivity)getActivity();
        mainActivity.getSupportActionBar().show();

*/

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
                    Toast.makeText(getContext(), "Uspesno unet", Toast.LENGTH_LONG).show();

                }catch (Exception e){

                    Toast.makeText(getContext(),"Morate uneti broj telefona", Toast.LENGTH_LONG).show();
                    phone.setError("Unesite brojeve u broj telefona");

                }
            }else{
                Toast.makeText(getContext(),"Neispravno unet mail!!!", Toast.LENGTH_LONG).show();
                eMail.setError("Email nije validan");
            }

        else{
            name.setError("morate popuniti ime");
            title.setError("morate popuniti title");
            eMail.setError("morate popuniti Email");
            phone.setError("morate popuniti phone");
            Toast.makeText(getContext(),"Morate popuniti sva polja!!", Toast.LENGTH_LONG).show();

        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
