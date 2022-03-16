package com.example.consultacep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.consultacep.activities.CepActivity;

public class MainActivity extends AppCompatActivity {
    private static final int TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Disable Night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), CepActivity.class));
            finish();
        }, TIMEOUT);
    }
}