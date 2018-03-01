package com.example.alex.recupalejandrogarcia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Alex on 01/03/2018.
 */

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(ActivitySplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 3000);


    }
}
