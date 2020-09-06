package com.example.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOGIN_KEY = "LOGIN_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);

        // The following line is for testing purposes. It forces a login.
        pref.edit().putBoolean(LOGIN_KEY, false).apply();

        if (pref.getBoolean(LOGIN_KEY, false)) {
            //has login
            System.out.println("Has already logged in. Skipping login activity.");
            startActivity(new Intent(this, MainActivity.class));
            //must finish this activity (the login activity will not be shown when click back in main activity)
            finish();
        }
        else {
            // Mark login
            pref.edit().putBoolean(LOGIN_KEY, false).apply();
            System.out.println("Just putBoolean into shared preferences.");
            // Do something
        }

        Button bt_login = findViewById(R.id.login);
        bt_login.setOnClickListener(this);
        Button bt_signUp = findViewById(R.id.signUp);
        bt_signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        System.out.println("Inside of onclick");
        switch(v.getId()){
            case R.id.signUp:{
                System.out.println("Inside case signUp");

                this.startActivity(new Intent(this, CreateAccountActivity.class));
            }
            break;
        }
    }
}