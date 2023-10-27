package com.example.CourseAppJava.models.User.Transactions;


import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.Path;

public interface ExpenseService {

    @GET("expense/user/{id}")
    Call<List<Expense>> getExpenseByUserId(@Path("id") String id);

}
