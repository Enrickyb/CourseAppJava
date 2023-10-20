package com.example.CourseAppJava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CourseAppJava.Services.UserService;
import com.example.CourseAppJava.models.User;
import com.example.CourseAppJava.models.UserRequest;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = (Button) findViewById(R.id.loginButton);
        ProgressBar loadingSpinner = findViewById(R.id.loadingSpinner);
        TextView txtEmail = (TextView) findViewById(R.id.emailEditText);
        TextView txtPassword = (TextView) findViewById(R.id.passwordEditText);




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setVisibility(View.INVISIBLE);
                loadingSpinner.setVisibility(View.VISIBLE);

                if (txtEmail.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    btnLogin.setVisibility(View.VISIBLE);
                    loadingSpinner.setVisibility(View.INVISIBLE);
                    return;
                }
                getData(txtEmail.getText().toString(), txtPassword.getText().toString());
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.49:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private void getData(String email, String password) {

        UserService service = retrofit.create(UserService.class);

        Call<User> userResponseCall = service.login(new UserRequest(email, password));
        Button btnLogin = findViewById(R.id.loginButton);
        ProgressBar loadingSpinner = findViewById(R.id.loadingSpinner);
        userResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                btnLogin.setVisibility(View.VISIBLE);
                loadingSpinner.setVisibility(View.INVISIBLE);

                if(response.isSuccessful()){

                    User user = response.body();


                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);


                }else{
                    Log.e("TAG", "onResponse: " + response.errorBody().toString());
                    Toast.makeText(MainActivity.this, "Login Failed (verify your credentials)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                btnLogin.setVisibility(View.VISIBLE);
                loadingSpinner.setVisibility(View.INVISIBLE);
                Log.e("TAG", "onResponse Failure: " + t.getMessage());
            }
        });

    }


}