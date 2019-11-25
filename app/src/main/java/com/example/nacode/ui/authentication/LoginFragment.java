package com.example.nacode.ui.authentication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nacode.R;

public class LoginFragment extends Fragment {


    private View lfView;
    private TextView lfForgotTv,lfSignUpTv;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        lfView = inflater.inflate(R.layout.fragment_login, container, false);
        lfForgotTv = (TextView) lfView.findViewById(R.id.lf_forgot_password);
        lfSignUpTv = (TextView)lfView.findViewById(R.id.lf_sign_up);


        lfView.findViewById(R.id.lf_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.signUpFragment);
            }
        });

        lfView.findViewById(R.id.lf_forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.forgotPasswordFragment);
            }
        });
        
        return lfView;
    }

}
