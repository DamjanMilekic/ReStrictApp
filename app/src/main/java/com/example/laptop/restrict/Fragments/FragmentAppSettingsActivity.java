package com.example.laptop.restrict.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.laptop.restrict.MainActivity;
import com.example.laptop.restrict.Model.Data;
import com.example.laptop.restrict.Model.Person;
import com.example.laptop.restrict.Model.User;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.RetrofitAppSettings.Client;
import com.example.laptop.restrict.RetrofitAppSettings.Service;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;


public class FragmentAppSettingsActivity extends Fragment {

    private FragmentActivity fragmentContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private static final String TAG = "APP";
    private static final int STORAGE_PERMISION_CODE = 123;
    private static final int CAMERA_REQUEST = 555;
    private static final int PICK_IMAGE_REQUEST = 321;

    EditText name, lastName, title, eMail, phone;


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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.app_settings, container, false);

        name = (EditText) view.findViewById(R.id.ime);
        lastName = (EditText)view.findViewById(R.id.prezime);
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

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog, null);

                Button takePhoto = (Button)mView.findViewById(R.id.takePhoto);
                Button chooseExisting = (Button)mView.findViewById(R.id.chooseExisting);
                Button cancel = (Button)mView.findViewById(R.id.cancelButton);


                mBuilder.setView(mView);


                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                takePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        takePicture();
                        dialog.dismiss();
                    }
                });

                chooseExisting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showFileChoser();
                        requestStoragePremision();
                        dialog.dismiss();
                    }
                });

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


                executePostRequest(name.getText().toString(),
                        lastName.getText().toString(),
                        title.getText().toString(),
                        eMail.getText().toString(),
                        phone.getText().toString());
                        //
                // TODO treba upload na server slikaaaaa


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
        Service service = Client.getApiClient().create(Service.class);

        Call<Person> call = service.getPerson(LoginFragment.api_token);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                User data = response.body().getUser();

                name.setText(data.getFirstName());
                lastName.setText(data.getLastName());
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


        if (ime.trim().length()>0 &&
                titl.trim().length()>0 &&
                mail.trim().length()>0 &&
                tel.trim().length()>0)

            if (isEmailValid(mail)) {
                try {
                    int t = Integer.parseInt(tel);
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

    private void executePostRequest(String postName, String postLastName, String postTitle
                    ,String postEmail,String postPhone){

        Service apiService = Client.getApiClient().create(Service.class);
        Call<Person> call = apiService.postPerson(postName,postLastName,postTitle,postEmail,postPhone, MainActivity.APP_TOKEN);

        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Toast.makeText(getContext(), "Uspesno Izmenjen", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Toast.makeText(getContext(), "NEUPSESNO", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void executreImagePostRequest(String fileUri){
        Service apiService = Client.getApiClient().create(Service.class);

      /*  File file = new File(filePath);

        RequestBody body = RequestBody.create(MultipartBody.FORM);*/
        File file = new File(fileUri);

        RequestBody filePart = RequestBody.create( MediaType.parse("image/*"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), filePart);

        Call<Person> call = apiService.postPicture(body, MainActivity.APP_TOKEN);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    private void requestStoragePremision(){
        if (ContextCompat.checkSelfPermission(fragmentContext, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions(fragmentContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(fragmentContext, "Permision Granted", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(fragmentContext, "Permision NOT Granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showFileChoser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void takePicture(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK){
                if (requestCode == PICK_IMAGE_REQUEST){
                    Uri slika = data.getData();
                    slikaIme.setImageURI(slika);
                    //executreImagePostRequest(slika);
                    //TODO TREBA UPLOAD SLIKU NA SERVER
                }else if (requestCode == CAMERA_REQUEST){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    slikaIme.setImageBitmap(imageBitmap);
                    //executreImagePostRequest();
                }
            }



    }
    Bitmap bitmap;
    private String imageToString(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte,Base64.DEFAULT);

    }

}
