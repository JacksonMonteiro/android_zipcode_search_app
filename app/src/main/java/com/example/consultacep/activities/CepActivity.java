package com.example.consultacep.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.consultacep.R;
import com.example.consultacep.model.Cep;
import com.example.consultacep.model.SimpleCallback;
import com.example.consultacep.service.CepServiceGenerator;
import com.example.consultacep.utils.CepMask;

import java.util.ArrayList;

public class CepActivity extends AppCompatActivity {
    private TextView street_view, complement_view, district_view, city_view, uf_view, ibge_view, gia_view, ddd_view, siafi_view;
<<<<<<< HEAD
=======
    ;
>>>>>>> b040973b95a29fa8941c1513bea2a190bf3a2349
    private Button consultCepBtn, trackActivityBtn;
    private EditText cepInput;
    private ArrayList<Cep> arrayCeps;
    Cep serverResponse = new Cep();
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        // Disable Night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Assign Variables
        cepInput = findViewById(R.id.cep_input);
        street_view = findViewById(R.id.street_value);
        complement_view = findViewById(R.id.complement_value);
        district_view = findViewById(R.id.district_value);
        city_view = findViewById(R.id.city_value);
        uf_view = findViewById(R.id.uf_value);
        ibge_view = findViewById(R.id.ibge_value);
        gia_view = findViewById(R.id.gia_value);
        ddd_view = findViewById(R.id.ddd_value);
        siafi_view = findViewById(R.id.siafi_value);

        consultCepBtn = findViewById(R.id.consult_cep_button);
        trackActivityBtn = findViewById(R.id.track_activity_button);

        // Cep Mask Application
        cepInput.addTextChangedListener(CepMask.mask(cepInput, CepMask.FORMAT_CEP));

        attachButtonListeners();
    }

    public void attachButtonListeners() {
        consultCepBtn.setOnClickListener(view -> {
            progress = new ProgressDialog(CepActivity.this);
            progress.setTitle("Consultando...");
            progress.show();

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            if (validate()) {
                if (checkNetwork()) {
                    CepServiceGenerator service = new CepServiceGenerator(CepActivity.this);

                    service.getCep(cepInput.getText().toString().replace("-", ""), new SimpleCallback<Cep>() {
                        @Override
                        public void onResponse(Cep response) {
                            Cep cep = response;

                            street_view.setText(cep.getLogradouro());
                            complement_view.setText(cep.getComplemento());
                            district_view.setText(cep.getBairro());
                            city_view.setText(cep.getLocalidade());
                            uf_view.setText(cep.getUf());
                            ibge_view.setText(cep.getIbge());
                            gia_view.setText(cep.getGia());
                            ddd_view.setText(cep.getDdd());
                            siafi_view.setText(cep.getSiafi());
                            progress.dismiss();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(getApplicationContext(), "Cep Inválido", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    });
                } else {
                    progress.dismiss();
                    Toast.makeText(this, "Sem conexão com Internet no momento", Toast.LENGTH_SHORT).show();
                }
            }
        });

        trackActivityBtn.setOnClickListener(view -> {
<<<<<<< HEAD
            startActivity(new Intent(getApplicationContext(), TrackingActivity.class));
=======
            startActivity(new Intent(CepActivity.this, TrackingActivity.class));
>>>>>>> b040973b95a29fa8941c1513bea2a190bf3a2349
        });
    }

    public boolean validate() {
        boolean result = false;
        String cep_value = cepInput.getText().toString().replace("-", "");

        if (cep_value.length() == 0) {
            cepInput.requestFocus();
            cepInput.setError("O Cep não pode estar vazio");
            progress.dismiss();
        } else if (cep_value.length() < 8) {
            cepInput.requestFocus();
            cepInput.setError("O Cep não pode ter menos que 8 digitos");
            progress.dismiss();
        } else if (cep_value.length() > 8) {
            cepInput.requestFocus();
            cepInput.setError("O Cep não pode ter mais que 8 digitos");
            progress.dismiss();
        } else {
            result = true;
        }

        return result;
    }

    public boolean checkNetwork() {
        boolean result = false;

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()) {
            result = true;
        }

        return result;
    }
}