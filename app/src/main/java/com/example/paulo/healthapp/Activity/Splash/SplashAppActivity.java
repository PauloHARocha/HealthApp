package com.example.paulo.healthapp.Activity.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.paulo.healthapp.Activity.PrincipalActivity;
import com.example.paulo.healthapp.R;


public class SplashAppActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_app);
        //atrasa a chamada da activity principal em um segundo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),PrincipalActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}