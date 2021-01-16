package com.sarkstechsolution.groceryhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    TextView logo,logoHouse;
    LottieAnimationView lottieAnimationView;
    private static int SPLASH_SCREEN_TIME_OUT=8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);


        logo = findViewById(R.id.logo);
        logoHouse = findViewById(R.id.logo_house);

        lottieAnimationView = findViewById(R.id.lottie);


        logo.animate().translationY(800).setDuration(4000).setStartDelay(3000);
        logoHouse.animate().translationY(1000).setDuration(2000).setStartDelay(3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreenActivity.this, IntroActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);


    }
}