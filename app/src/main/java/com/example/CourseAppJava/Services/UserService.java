package com.example.CourseAppJava.Services;

import com.example.CourseAppJava.models.User.User;
import com.example.CourseAppJava.models.User.UserRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UserService {

    @POST("auth/login")
    Call<ResponseBody> login(@Body UserRequest userRequest);

    @POST("auth/register")
    Call<ResponseBody> register(@Body User user);

    @POST("auth/logout")
    Call<ResponseBody> logout();

    @POST("auth/validatetoken")
    Call<User> validate(@Header("Authorization") String token);

}
