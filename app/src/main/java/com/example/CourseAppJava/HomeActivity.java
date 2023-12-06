package com.example.CourseAppJava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;
import android.util.Log;

import com.example.CourseAppJava.Adapters.ExpenseAdapter;
import com.example.CourseAppJava.Services.UserService;
import com.example.CourseAppJava.ViewModel.ExpenseViewModel;
import com.example.CourseAppJava.models.User.Transactions.Expense;
import com.example.CourseAppJava.models.User.Transactions.ExpenseService;
import com.example.CourseAppJava.models.User.User;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeActivity extends AppCompatActivity {


    private Retrofit retrofit;



    private ExpenseAdapter expenseAdapter;
    private List<Expense> expenses = new ArrayList<>();

    private Bundle userBundle = new Bundle();
    private Bundle expensesBundle = new Bundle();

    private NavHostFragment navHostFragment;
    private NavController navController;

    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.49:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        initNavigation();


        String token = getIntent().getStringExtra("token");


        getData(token);







    }

    private void initNavigation(){
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

        expenseAdapter = new ExpenseAdapter(this, expenses);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


    private void initBadge(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.transactionsFragment);
        badge.setVisible(true);
        Log.i("TAG", "initBadge: " + expenses.size());
        badge.setNumber(expenses.size());
    }


    private void getData(String token) {

        UserService userService = retrofit.create(UserService.class);

        String authorizationHeader = "Bearer " + token;

        Log.i("TAG", "getData: " + authorizationHeader);



        Call<User> userResponseCall = userService.validate(authorizationHeader);

    userResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    if (user != null) {

                        getExpenses(authorizationHeader, user.getId());
                    } else {
                        Log.e("TAG", "User object is null");

                    }


                }else{
                    Log.e("TAG", "onResponse data Error : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Log.e("TAG", "onResponse data Failure: " + t.getMessage());
            }
        });




    }

    private void getExpenses(String token, String id) {
        ExpenseService service = retrofit.create(ExpenseService.class);

        Call<List<Expense>> expenseResponseCall = service.getExpenseByUserId(token, id);


        expenseResponseCall.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if(response.isSuccessful()){
                    expenses = response.body();
                    expensesBundle.putSerializable("expenses", (ArrayList<Expense>) expenses);
                    expenseViewModel.setExpenses(expenses);
                    initBadge();

                }else{
                    Log.e("TAG", "onResponse expense Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                Log.e("TAG", "onResponse expense Failure: " + t.getMessage());
            }
        });



    }


}