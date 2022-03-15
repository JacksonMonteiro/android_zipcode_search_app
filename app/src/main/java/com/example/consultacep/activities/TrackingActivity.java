package com.example.consultacep.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consultacep.R;
import com.example.consultacep.model.SimpleCallback;
import com.example.consultacep.model.TrackingCode;
import com.example.consultacep.service.TrackingServiceGenerator;
import com.google.gson.Gson;

public class TrackingActivity extends AppCompatActivity {
    private EditText trackCodeInput;
    private TextView text_code;
    private Button trackButton;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        // Disable Night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        trackCodeInput = findViewById(R.id.track_code_input);
        text_code = findViewById(R.id.code_value);
        trackButton = findViewById(R.id.track_button);

        attachButtonListeners();
    }

    public void attachButtonListeners() {
        trackButton.setOnClickListener(view -> {
            progress = new ProgressDialog(TrackingActivity.this);
            progress.setTitle("Rastreando...");
            progress.show();

            TrackingServiceGenerator service = new TrackingServiceGenerator(TrackingActivity.this);

            service.getTracking(trackCodeInput.getText().toString(), new SimpleCallback<TrackingCode>() {
                @Override
                public void onResponse(TrackingCode response) {
                    TrackingCode code = response;
                    text_code.setText(code.getCodigo());
                    progress.dismiss();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), "Código Inválido", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            });
        });
    }
}