package com.example.nacode.ui.authentication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nacode.R;

public class SignUpFragment extends Fragment {

    private TextView sufLogin;
    private View sufView;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sufView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        sufLogin = (TextView)sufView.findViewById(R.id.suf_login);
        sufLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });
        return sufView;
    }

}
