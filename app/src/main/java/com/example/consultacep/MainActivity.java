package com.example.consultacep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.consultacep.activities.CepActivity;

public class MainActivity extends AppCompatActivity {
    private static final int TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), CepActivity.class));
            finish();
        }, TIMEOUT);
    }
}