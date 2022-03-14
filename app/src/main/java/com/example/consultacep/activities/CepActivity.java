package com.example.consultacep.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consultacep.R;
import com.example.consultacep.model.Cep;
import com.example.consultacep.model.SimpleCallback;
import com.example.consultacep.service.CepServiceGenerator;
import com.example.consultacep.service.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CepActivity extends AppCompatActivity {
    private TextView street_view;
    private Button consultCepBtn;
    private EditText cepInput;
    private ArrayList<Cep> arrayCeps;
    Cep serverResponse = new Cep();
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        // Assign Variables
        cepInput = findViewById(R.id.cep_input);
        street_view = findViewById(R.id.street_value);
        consultCepBtn = findViewById(R.id.consult_cep_button);

        attachButtonListeners();
    }

    public void attachButtonListeners() {
        consultCepBtn.setOnClickListener(view -> {
            progress = new ProgressDialog(CepActivity.this);
            progress.setTitle("Consultando...");
            progress.show();

            if(!cepInput.getText().toString().isEmpty()) {
                CepServiceGenerator service = new CepServiceGenerator(CepActivity.this);
                service.getCep(cepInput.getText().toString(), new SimpleCallback<Cep>() {
                    @Override
                    public void onResponse(Cep response) {
                        Cep cep = response;
                        street_view.setText(cep.getLogradouro());
                        progress.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Cep Vazio", Toast.LENGTH_SHORT).show();
            }

        });
    }

}