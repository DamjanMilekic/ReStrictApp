package com.example.laptop.restrict.Fragments;


import android.os.Handler;
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

    String email = "", password = "";

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

        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (email != null && email.trim().length() > 0 && password != null && password.trim().length() > 0) {

            if (FragmentAppSettingsActivity.isEmailValid(email.trim().toString())) {

                apiService = Client.getApiClient().create(Service.class);
                Call<ProjectStatusLogin> call = apiService.loginToApp(new LoginRequest(email, password));
                call.enqueue(new Callback<ProjectStatusLogin>() {

                    @Override
                    public void onResponse(Call<ProjectStatusLogin> call, Response<ProjectStatusLogin> response) {

                            if (response.body() != null) {

                                projectStatusLogin = (ProjectStatusLogin) response.body();
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

                                } else {
                                    Toast.makeText(getContext(), "Login status: " + status, Toast.LENGTH_LONG).show();
                                    emailEdit.setText("");
                                }

                            } else {
                                Toast.makeText(getContext(), "Pogresni podaci za logovanje", Toast.LENGTH_LONG).show();
                                Log.d("LOGIN", "Step 5");
                            }

                    }

                    @Override
                    public void onFailure(Call<ProjectStatusLogin> call, Throwable t) {
                        Toast.makeText(getContext(), "Problem sa povezivanjem.", Toast.LENGTH_SHORT).show();
                    }
                });

                /*Log.d("ResponseBody", "test0");
                apiService = Client.getApiClient().create(Service.class);
                Call<ResponseBody> call = apiService.loginToApplication(new LoginRequest(email, password));
                call.enqueue(new Callback<ResponseBody>() {

                    String responseBodyJSON;

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d("ResponseBody", "test");

                        if (response.isSuccessful()) {
                            Gson gson = new Gson();

                            try {

                                responseBodyJSON = response.body().string();

                            } catch (IOException e) {

                                e.printStackTrace();

                            }

                            Log.d("ResponseBody", responseBodyJSON);

                        } else {

                            Toast.makeText(this, response.body().string(), Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });*/


            } else {
                Toast.makeText(context, "Pogresan format email-a.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Morate popuniti sva polja.", Toast.LENGTH_LONG).show();
        }

    }

}
