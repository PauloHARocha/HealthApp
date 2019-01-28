package com.example.paulo.healthapp.Activity.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.paulo.healthapp.Dao.Dao;


public class SplashLudusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicializa o banco de dados
        Dao.getInstance(SplashLudusActivity.this);

        startActivity(new Intent(getApplicationContext(),SplashAppActivity.class));
        finish();
    }
}
