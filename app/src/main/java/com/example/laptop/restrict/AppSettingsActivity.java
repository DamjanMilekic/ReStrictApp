package com.example.laptop.restrict;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.laptop.restrict.Model.Osoba;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by durma on 2.2.18..
 */

public class AppSettingsActivity extends AppCompatActivity {
    EditText name, title, eMail, phone;
    Button test;


    ImageView slikaIme, slikax, slikaRotateLeft, slikaRotateRight ;

    LinearLayout linearLayoutSlicicaIText;

    private static final String TAG = "APP";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);

        name = (EditText) findViewById(R.id.ime);
        title = (EditText) findViewById(R.id.title);
        eMail = (EditText) findViewById(R.id.eMail);
        phone = (EditText) findViewById(R.id.phone);

        slikaIme = (ImageView) findViewById(R.id.sl);

        //Slike za manipulaciju sa slikom

        slikax = (ImageView) findViewById(R.id.imagex);
        slikaRotateLeft = (ImageView) findViewById(R.id.imagerotateLeft);
        slikaRotateRight = (ImageView) findViewById(R.id.imagerotateRight);


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


        //layout za elemente u kojoj su slicica sa kamerom i mali tekst upload picture
        linearLayoutSlicicaIText = (LinearLayout) findViewById(R.id.slicicaItext);

        //dodavanje ako slika  nema content, i ako ima brise placeholder
        if (slikaIme.getDrawable()==null){
            slikaIme.setImageResource(R.drawable.backround_circle);
            Log.d(TAG, "nema slike");

        }
        else {
            linearLayoutSlicicaIText.setVisibility(View.GONE);
        }



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
                    Toast.makeText(AppSettingsActivity.this, "Uspesno unet", Toast.LENGTH_LONG).show();

                }catch (Exception e){

                    Toast.makeText(AppSettingsActivity.this,"Morate uneti broj telefona", Toast.LENGTH_LONG).show();
                    phone.setError("Unesite brojeve u broj telefona");

                }
            }else{
                Toast.makeText(AppSettingsActivity.this,"Neispravno unet mail!!!", Toast.LENGTH_LONG).show();
                eMail.setError("Email nije validan");
            }

        else{
            name.setError("morate popuniti ime");
            title.setError("morate popuniti title");
            eMail.setError("morate popuniti Email");
            phone.setError("morate popuniti phone");
            Toast.makeText(AppSettingsActivity.this,"Morate popuniti sva polja!!", Toast.LENGTH_LONG).show();

        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
