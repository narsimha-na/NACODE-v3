package com.example.nacode.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiCalls {
    @FormUrlEncoded
    @POST("auth/register.php")
    Call<AuthenticationResponse> userSignUp(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender,
            @Field("dob") String dob,
            @Field("soy") String soy,
            @Field("college") String college,
            @Field("level") String level,
            @Field("role") String role

    );

}
