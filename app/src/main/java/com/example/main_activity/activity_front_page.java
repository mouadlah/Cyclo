package com.example.main_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;


public class activity_front_page extends AppCompatActivity {
    private TextView admin;
    private TextView club;
    private TextView user_type;
    private TextView login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        admin = (TextView) findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openLogin();
            }
        });
        club = (TextView) findViewById(R.id.club);
        club.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });

        user_type = (TextView) findViewById(R.id.user_type);
        user_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSignUp();
            }
        });

        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(activity_front_page.this,activity_login_page.class);
        startActivity(intent);
    }
    public void openSignUp(){
        Intent intent = new Intent(activity_front_page.this,login_page.class);
        startActivity(intent);
    }
}