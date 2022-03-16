package com.example.consultacep.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CepActivity extends AppCompatActivity {
    private TextView street_view, complement_view, district_view, city_view, uf_view, ibge_view, gia_view, ddd_view, siafi_view;
    private Button consultCepBtn, trackActivityBtn;
    private EditText cepInput;
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

            cleanTextViews();

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
                            
                            if (!response.isErro()) {
                                street_view.setText(response.getLogradouro());
                                complement_view.setText(response.getComplemento());
                                district_view.setText(response.getBairro());
                                city_view.setText(response.getLocalidade());
                                uf_view.setText(response.getUf());
                                siafi_view.setText(response.getSiafi());
                                ibge_view.setText(response.getIbge());
                                gia_view.setText(response.getGia());
                                ddd_view.setText(response.getDdd());
                            } else {
                                Toast.makeText(CepActivity.this, "CEP Inválido, por favor, tente outro", Toast.LENGTH_LONG).show();
                            }

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

        trackActivityBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TrackingActivity.class)));
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

    public void cleanTextViews() {
        street_view.setText("");
        complement_view.setText("");
        district_view.setText("");
        city_view.setText("");
        uf_view.setText("");
        siafi_view.setText("");
        ibge_view.setText("");
        gia_view.setText("");
        ddd_view.setText("");
    }
}