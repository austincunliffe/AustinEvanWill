package com.example.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOGIN_KEY = "LOGIN_KEY";

    EditText et_uname, et_pw;
    String un, user_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        et_uname = findViewById(R.id.username);
        et_pw = findViewById(R.id.password);

        SharedPreferences pref = getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);

//         The following line is for testing purposes. It forces credentials.
        pref.edit().putBoolean(LOGIN_KEY, false).apply();

        if (pref.getBoolean(LOGIN_KEY, false)) {
            startActivity(new Intent(this, MainDrawerActivity.class));
            finish();
        }

        Button bt_login = findViewById(R.id.login);
        bt_login.setOnClickListener(this);
        Button bt_signUp = findViewById(R.id.signUp);
        bt_signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signUp:{
                this.startActivity(new Intent(this, CreateAccountActivity.class));
                break;
            }

            case R.id.login:{
                SharedPreferences pref = getSharedPreferences("com.example.lifestyleapp",
                        Context.MODE_PRIVATE);

                un = et_uname.getText().toString();
                user_pw = et_pw.getText().toString();

                String username = pref.getString("username", null);
                String pw = pref.getString("password", null);

                if (username == null || pw == null || (!username.matches(un)) || (!user_pw.matches(pw))) {
                    Toast.makeText(this, "Invalid login.", Toast.LENGTH_SHORT).show();
                } else {
                    pref.edit().putBoolean(LOGIN_KEY, true).apply();
//                    startActivity(new Intent(this, UserProfileActivity.class));
                    startActivity(new Intent(this, MainDrawerActivity.class));
                }
                break;
            }
        }
    }
}