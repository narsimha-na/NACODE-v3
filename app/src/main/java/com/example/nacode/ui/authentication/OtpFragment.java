package com.example.nacode.ui.authentication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nacode.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OtpView;

import java.util.concurrent.TimeUnit;

public class OtpFragment extends Fragment {

    private View ofView;
    private EditText ofCodeView, ofNumberView;
    private String ofCode, ofNumber;
    private EditText ofOtpView;
    private LinearLayout ofNumberLayout, ofOtpLayout;
    private FirebaseAuth ofFirebaseAuth;
    private Button ofOtpButton;
    private String mVerificationId;
    private int counter;


    public OtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ofView = inflater.inflate(R.layout.fragment_otp, container, false);

        ofFirebaseAuth = FirebaseAuth.getInstance();

        ofNumberView = (EditText) ofView.findViewById(R.id.of_number);
        ofCodeView = (EditText) ofView.findViewById(R.id.of_country_code);
        ofOtpView = (EditText) ofView.findViewById(R.id.of_otp_view);
        ofOtpButton = (Button) ofView.findViewById(R.id.of_verify_otp);
        ofNumberLayout = (LinearLayout) ofView.findViewById(R.id.of_get_number_layout);
         ofOtpLayout = (LinearLayout) ofView.findViewById(R.id.of_get_otp_layout);

        // Getting the OTP for the number
        ofView.findViewById(R.id.of_get_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String number = ofNumberView.getText().toString();
                final String code = ofCodeView.getText().toString();

                if (code.isEmpty() && number.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the following fields", Toast.LENGTH_SHORT).show();
                } else if (number.length() != 10) {
                    Toast.makeText(getContext(), "Please enter 10 digit mobile number"+number, Toast.LENGTH_SHORT).show();
                } else if (code.length() < 1) {
                    Toast.makeText(getContext(), "Please enter a proper country code"+code+code.length(), Toast.LENGTH_SHORT).show();
                } else {
                    ofNumberLayout.setVisibility(View.GONE);
                    ofOtpLayout.setVisibility(View.VISIBLE);
                    sendVerificationCode(code, number);

                }


            }
        });


        ofOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(ofOtpView.getText().toString());
            }
        });
        return ofView;
    }

    private void sendVerificationCode(String code, String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+"91" +number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks
        );
    }

    // This Method is used to get the OTP from the user
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the six digit code from the sms
            String code = phoneAuthCredential.getSmsCode();
            /*
            Sometimes the sms may not be detected automatically in this case it will be null so user has to enter the code manually
             */
            if (code != null) {
                ofOtpView.setText(code);
                verifyCode(code);
            }else {
                Toast.makeText(getContext(), "Ffail"+code, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //Storing the verification id that is sent to user
            mVerificationId = s;
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), "Verification Failed" + e, Toast.LENGTH_LONG).show();
            Log.e("errOF",e.getMessage());
        }
    };

    // This method is used to verify the OTP code  of the user
    private void verifyCode(String code) {
        //Creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //Signing the user
        ofFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "phone number verified successfully ", Toast.LENGTH_SHORT).show();
                            Log.e("errOF","sucesss");
                        }
                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error : "+e, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
    }

}


