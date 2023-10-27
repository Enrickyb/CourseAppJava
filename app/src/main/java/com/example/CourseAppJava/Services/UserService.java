package com.example.CourseAppJava.Services;

import com.example.CourseAppJava.models.User.User;
import com.example.CourseAppJava.models.User.UserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface UserService {

    @POST("auth/login")
    Call<User> login(@Body UserRequest userRequest);

}
