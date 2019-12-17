package com.example.nacode.ui.authentication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacode.R;

import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    private TextView suLogin;
    private EditText suNameView,suEmailView,suPasswordView,suMobileView;
    private View suView;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        suView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        suNameView = (EditText) suView.findViewById(R.id.su_name);
        suEmailView = (EditText) suView.findViewById(R.id.su_email);
        suPasswordView = (EditText)suView.findViewById(R.id.su_password);
        suMobileView = (EditText)suView.findViewById(R.id.su_phone);

        suLogin = (TextView)suView.findViewById(R.id.su_login);
        suLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });

        suView.findViewById(R.id.su_sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,password,phone;
                name = suNameView.getText().toString();
                email = suEmailView.getText().toString();
                password = suPasswordView.getText().toString();
                phone = suMobileView.getText().toString();
                if(name.isEmpty() && email.isEmpty() && password.isEmpty() && phone.isEmpty()){
                    Toast.makeText(getContext(), "Fields are empty !", Toast.LENGTH_SHORT).show();
                }else if(phone.length() != 10){
                    Toast.makeText(getContext(), "Enter a valid phone number !", Toast.LENGTH_SHORT).show();
                }else if(!isEmailValid(email)){
                    Toast.makeText(getContext(), "Enter a valid email address !", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
        return suView;
    }

    public static boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
