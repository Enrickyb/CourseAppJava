package com.example.CourseAppJava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.CourseAppJava.models.User;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView username = (TextView) findViewById(R.id.userNameTextView);

        User user = (User) getIntent().getSerializableExtra("user");

        username.setText(user.getName());

        Log.i("user intent", user.getName());


    }
}