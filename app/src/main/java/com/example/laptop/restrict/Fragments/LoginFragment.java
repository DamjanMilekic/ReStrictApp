package com.example.laptop.restrict.Fragments;


import android.support.v4.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptop.restrict.Interfaces.ILoginMain;
import com.example.laptop.restrict.Model.LoginRequest;
import com.example.laptop.restrict.Model.ProjectStatusLogin;
import com.example.laptop.restrict.RetrofitAppSettings.Client;
import com.example.laptop.restrict.R;
import com.example.laptop.restrict.RetrofitAppSettings.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class LoginFragment extends Fragment implements View.OnClickListener {

    private ILoginMain listenerLoginMain;
    HomeFragment homeFragment;
    EditText  passwordEdit,emailEdit;
    Button btnLogin;
    View view;
    Context context;

    Client apiClient;
    Service apiService;

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

        return view;
    }

    @Override
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        emailEdit = (EditText) view.findViewById(R.id.email);
        passwordEdit = (EditText) view.findViewById(R.id.password);
        btnLogin = (Button) view.findViewById(R.id.login_btn);



        btnLogin.setOnClickListener(this);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        listenerLoginMain = null;
    }


    @Override
    public void onClick(View v) {

        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (email != null && password != null) {
            if (email.contains("@") && email.contains(".")) {
                apiService = Client.getApiClient().create(Service.class);
                Call<ProjectStatusLogin> call = apiService.loginToApp(new LoginRequest(email, password));
                call.enqueue(new Callback<ProjectStatusLogin>() {
                    @Override
                    public void onResponse(Call<ProjectStatusLogin> call, Response<ProjectStatusLogin> response) {

                        ProjectStatusLogin projectStatusLogin = response.body();
                        String status = projectStatusLogin.getStatus();
                        api_token += projectStatusLogin.getToken();

                        if (status != null && status.equals("success")) {

                            Fragment newFragment = new HomeFragment();
                            Bundle args = new Bundle();
                            args.putSerializable("login", projectStatusLogin);
                            newFragment.setArguments(args);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame, newFragment);
                            transaction.commit();

                            Toast.makeText(getContext(), "Login status: " + status, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getContext(), "Login status: " + status, Toast.LENGTH_LONG).show();
                            emailEdit.setError("Proverite da li ste ispravno uneli email.");
                            emailEdit.setError("Proverite da li ste ispravno uneli password");
                        }

                    }

                    @Override
                    public void onFailure(Call<ProjectStatusLogin> call, Throwable t) {
                        Toast.makeText(getContext(), "Problem sa povezivanjem.", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(context, "Niste ispravno uneli email.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Morate popuniti sva polja.", Toast.LENGTH_LONG).show();
        }

    }
}
