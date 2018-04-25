package com.example.laptop.restrict.Fragments;


import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptop.restrict.Interfaces.ILoginMain;
import com.example.laptop.restrict.Model.DatumPopup;
import com.example.laptop.restrict.Model.LoginRequest;
import com.example.laptop.restrict.Model.NotificationPopup;
import com.example.laptop.restrict.Model.ProjectStatusLogin;
import com.example.laptop.restrict.RetrofitAppSettings.Client;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.RetrofitAppSettings.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class LoginFragment extends Fragment implements View.OnClickListener {

    public static int user_id;

    private ILoginMain listenerLoginMain;
    HomeFragment homeFragment;
    EditText  passwordEdit,emailEdit;
    Button btnLogin;
    View view;
    Context context;

    String email = "", password = "";

    List<DatumPopup> datumPopups;
    Client apiClient;
    Service apiService;
    ProjectStatusLogin projectStatusLogin;
    Handler handler;

    public static String api_token = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.login_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        emailEdit = (EditText) view.findViewById(R.id.email);
        passwordEdit = (EditText) view.findViewById(R.id.password);
        projectStatusLogin = new ProjectStatusLogin();
        handler = new Handler(getContext().getMainLooper());

        datumPopups = new ArrayList<DatumPopup>();
        return view;
    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if(context instanceof ILoginMain)
        {
            listenerLoginMain = (ILoginMain) context;
        }
        else
        {
            throw  new ClassCastException(context.toString()+" must implement LoginFragment.ILoginMain");
        }
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        emailEdit = (EditText) view.findViewById(R.id.email);
        passwordEdit = (EditText) view.findViewById(R.id.password);
        btnLogin = (Button) view.findViewById(R.id.login_btn);
        passwordEdit.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("emailEdit", emailEdit.getText().toString());
        outState.putString("passwordEdit", passwordEdit.getText().toString());
        outState.putString("email", email);
        outState.putString("password", password);

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            emailEdit.setText(savedInstanceState.getString("emailEdit"));
            passwordEdit.setText(savedInstanceState.getString("passwordEdit"));
            email = savedInstanceState.getString("email");
            password = savedInstanceState.getString("password");
        }
    }



    @Override
    public void onResume() {
        super.onResume();

        emailEdit.setText(email);
        passwordEdit.setText(password);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerLoginMain = null;
    }


    @Override
    public void onClick(View v) {

        btnLogin.setEnabled(false);

        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (email != null && email.trim().length() > 0 && password != null && password.trim().length() > 0) {

            if (FragmentAppSettingsActivity.isEmailValid(email.trim().toString())) {

                apiService = Client.getApiClient().create(Service.class);
                Call<ProjectStatusLogin> call = apiService.loginToApp(new LoginRequest(email, password));
                Log.d("LOGIN", "Step 7");

                call.enqueue(new Callback<ProjectStatusLogin>() {

                    @Override
                    public void onResponse(Call<ProjectStatusLogin> call, Response<ProjectStatusLogin> response) {

                        if (response.body() != null) {

                            projectStatusLogin = (ProjectStatusLogin) response.body();
                            String status = projectStatusLogin.getStatus();
                            api_token = projectStatusLogin.getToken();
                            user_id = projectStatusLogin.getUserId();


                            if (status != null) {


                                Fragment newFragment = new HomeFragment();
                                Bundle args = new Bundle();
                                args.putSerializable("login", projectStatusLogin);
                                args.putParcelableArrayList("notif", (ArrayList<? extends Parcelable>) datumPopups);
                                newFragment.setArguments(args);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame, newFragment);
                                transaction.commit();
                                btnLogin.setEnabled(true);


                            } else {
                                Toast.makeText(getContext(), "Login status: " + response, Toast.LENGTH_LONG).show();
                                emailEdit.setText("");
                                btnLogin.setEnabled(true);


                                //  getNotification();

                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ProjectStatusLogin> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection error", Toast.LENGTH_SHORT).show();
                        Log.d("LOGIN", "Step 6");
                        btnLogin.setEnabled(true);


                    }
                });





            } else {
                Toast.makeText(context, "Wrong email format", Toast.LENGTH_LONG).show();
                btnLogin.setEnabled(true);


            }
        } else {
            Toast.makeText(context, "You need to fill all fields!", Toast.LENGTH_LONG).show();
            btnLogin.setEnabled(true);

        }

    }



    private void getNotification() {

        Service apiService = Client.getApiClient().create(Service.class);
        Call<NotificationPopup> call = apiService.getNotificationsPopup(api_token);

        call.enqueue(new Callback<NotificationPopup>() {
            @Override
            public void onResponse(Call<NotificationPopup> call, Response<NotificationPopup> response) {

                if (response.body() != null) {

                    NotificationPopup notificationPopup = response.body();
                    datumPopups = notificationPopup.getData();

                    if (response != null) {

                        Fragment newFragment = new HomeFragment();
                        Bundle args = new Bundle();
                        args.putSerializable("login", projectStatusLogin);
                        args.putParcelableArrayList("notif", (ArrayList<? extends Parcelable>) datumPopups);
                        newFragment.setArguments(args);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, newFragment);
                        transaction.commit();

                    } else {
                        Toast.makeText(getContext(), "Login status: " + response, Toast.LENGTH_LONG).show();
                        emailEdit.setText("");
                    }

                } else {
                    Toast.makeText(getContext(), "Pogresni podaci za logovanje", Toast.LENGTH_LONG).show();
                    Log.d("LOGIN", "Step 5");
                }

            }


            @Override
            public void onFailure(Call<NotificationPopup> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail on server", Toast.LENGTH_LONG).show();

            }
        });

    }
        public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
            @Override
            public CharSequence getTransformation(CharSequence source, View view) {
                return new PasswordCharSequence(source);
            }

            private class PasswordCharSequence implements CharSequence {
                private CharSequence mSource;

                public PasswordCharSequence(CharSequence source) {
                    mSource = source; // Store char sequence
                }

                public char charAt(int index) {
                    return '*'; // This is the important part
                }

                public int length() {
                    return mSource.length(); // Return default
                }

                public CharSequence subSequence(int start, int end) {
                    return mSource.subSequence(start, end); // Return default
                }
            }
        }
    }

