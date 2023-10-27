package com.example.CourseAppJava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import com.example.CourseAppJava.Adapters.ExpenseAdapter;
import com.example.CourseAppJava.models.User.Transactions.Expense;
import com.example.CourseAppJava.models.User.Transactions.ExpenseService;
import com.example.CourseAppJava.models.User.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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
    private ViewPager2 viewPager;

    private Bundle userBundle = new Bundle();
    private Bundle expensesBundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        TextView username = (TextView) findViewById(R.id.userNameTextView);

        User user = (User) getIntent().getSerializableExtra("user");

        username.setText(user.getName());
        userBundle.putSerializable("user", user);

        Log.i("user intent", user.getName());


        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.49:8090/transaction/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        getData(user.getId());

        viewPager = findViewById(R.id.viewPager);

        configTabLayout();



    }

    private void configTabLayout(){

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);


        viewPagerAdapter.addFragment(new HomeFragment(), "Home", userBundle);
        viewPagerAdapter.addFragment(new transactionsFragment(), "Transactions", expensesBundle);

        viewPager.setOffscreenPageLimit(viewPagerAdapter.getItemCount());
        expenseAdapter = new ExpenseAdapter(this, expenses);

        TabLayoutMediator mediator = new TabLayoutMediator(
                findViewById(R.id.tabLayout),
                viewPager,
                (tab, position) -> tab.setText(viewPagerAdapter.getTitle(position))
        );

        mediator.attach();
    }

    private void getData(String user_id) {
        ExpenseService service = retrofit.create(ExpenseService.class);

        Call<List<Expense>> expenseResponseCall = service.getExpenseByUserId(user_id);

        expenseResponseCall.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {

                if(response.isSuccessful()){

                    List<Expense> responseExpenses = response.body();
                    expenses.clear();
                    if (responseExpenses != null) {
                        expenses.addAll(responseExpenses);

                        float totalExpense = 0;
                        for (Expense expense : expenses) {
                            totalExpense += expense.getValue();
                        }
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                        String formattedTotalExpense = currencyFormat.format(totalExpense);

                        expensesBundle.putSerializable("expenses", (ArrayList<Expense>) expenses);



                    }
                    expenseAdapter.notifyDataSetChanged();



                }else{
                    Log.e("TAG", "onResponse Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {

                Log.e("TAG", "onResponse Failure: " + t.getMessage());
            }
        });

    }



}