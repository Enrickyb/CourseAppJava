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
import com.example.CourseAppJava.models.User.User;
import com.example.CourseAppJava.models.User.UserRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
     Button btnLogin;
    ProgressBar loadingSpinner;
    TextView txtEmail;
    TextView txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.loginButton);
        loadingSpinner = findViewById(R.id.loadingSpinner);
        txtEmail = (TextView) findViewById(R.id.emailEditText);
        txtPassword = (TextView) findViewById(R.id.passwordEditText);



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
                .addConverterFactory(GsonConverterFactory.create(getLenientGson()))
                .build();

    }

    private Gson getLenientGson() {
        return new GsonBuilder().setLenient().create();
    }

    private void getData(String email, String password) {

        UserService service = retrofit.create(UserService.class);


        Button btnLogin = findViewById(R.id.loginButton);
        ProgressBar loadingSpinner = findViewById(R.id.loadingSpinner);
        Call<ResponseBody> userResponseCall = service.login(new UserRequest(email, password));

        userResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                btnLogin.setVisibility(View.VISIBLE);
                loadingSpinner.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {
                    try {
                        String token = response.body().string();  // Converta o corpo da resposta para uma string
                        Log.i("token1", "onResponse: " + token);

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Lidar com o erro de leitura do corpo da resposta
                    }
                } else {
                    Log.e("TAG", "onResponse: " + response.errorBody().toString());
                    Toast.makeText(MainActivity.this, "Login Failed (verify your credentials)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                btnLogin.setVisibility(View.VISIBLE);
                loadingSpinner.setVisibility(View.INVISIBLE);
                Log.e("TAG", "onResponse Failure: " + t.getMessage());
            }
        });

    }


}