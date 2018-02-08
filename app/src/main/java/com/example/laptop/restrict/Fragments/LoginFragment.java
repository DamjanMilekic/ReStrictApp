package com.example.laptop.restrict.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.laptop.restrict.Interfaces.ILoginMain;
import com.example.laptop.restrict.R;

public  class LoginFragment extends Fragment {

    private ILoginMain listenerLoginMain;
    EditText  passwordEdit,emailEdit;
    Button btnLogin;
    View view;
    Context contextt;

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

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.contextt = context;

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



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerLoginMain.onClick();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerLoginMain = null;
    }



}