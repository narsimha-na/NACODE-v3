package com.example.nacode.ui.authentication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.example.nacode.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.mukesh.OtpView;

import java.util.concurrent.TimeUnit;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
    private OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;



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
                    Toast.makeText(getContext(), "Please enter 10 digit mobile number" + number, Toast.LENGTH_SHORT).show();
                } else if (code.length() < 1) {
                    Toast.makeText(getContext(), "Please enter a proper country code" + code + code.length(), Toast.LENGTH_SHORT).show();
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
//                verifyCode(ofOtpView.getText().toString());
                verifyPhoneNumberWithCode(mVerificationId,ofOtpView.getText().toString());
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("code", "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(getContext(), "Error : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d("Code", "onCodeSent:" + verificationId);

                mVerificationId = verificationId;
                mResendToken = token;
            }
        };


        return ofView;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        ofFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "OTP Verified Successfully !", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(ofView).navigate(R.id.signUpFragment);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "OTP Verification Failed", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }

    private void sendVerificationCode(String code, String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+code+number,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                mCallbacks);
    }


}