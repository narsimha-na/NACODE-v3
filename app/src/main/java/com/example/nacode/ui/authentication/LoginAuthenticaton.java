package com.example.nacode.ui.authentication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nacode.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginAuthenticaton extends Fragment {


    public LoginAuthenticaton() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_authenticaton, container, false);
    }

}
