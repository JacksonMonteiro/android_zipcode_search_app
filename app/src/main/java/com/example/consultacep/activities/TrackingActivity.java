package com.example.consultacep.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consultacep.R;
import com.example.consultacep.model.SimpleCallback;
import com.example.consultacep.model.TrackingCode;
import com.example.consultacep.service.TrackingServiceGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class TrackingActivity extends AppCompatActivity {
    private ImageButton cepActivityButton;
    private Button consultTrackBtn;
    private EditText trackCodeInput;
    private TextView text_code, text_desc, text_city, text_uf;
    private ProgressDialog progress;
    private TrackingCode track = new TrackingCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        trackCodeInput = findViewById(R.id.track_code_input);

        text_code = findViewById(R.id.code_value);
        text_desc = findViewById(R.id.track_code_value);
        text_city = findViewById(R.id.city_value);
        text_uf = findViewById(R.id.uf_value);


        cepActivityButton = findViewById(R.id.cep_activity_button);
        consultTrackBtn = findViewById(R.id.consult_track_button);

        attachButtonListeners();
    }

    public void attachButtonListeners() {
        consultTrackBtn.setOnClickListener(view -> {
            progress = new ProgressDialog(TrackingActivity.this);
            progress.setTitle("Consultando...");
            progress.show();

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            if (validate()) {
                if (checkNetwork()) {
                    TrackingServiceGenerator service = new TrackingServiceGenerator(TrackingActivity.this);

                    service.getTracking(trackCodeInput.getText().toString(), new SimpleCallback<TrackingCode>() {
                        @Override
                        public void onResponse(TrackingCode response) {
                            TrackingCode code = response;

                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            String jsonString = gson.toJson(code.getObjetos().get(0));

                            try {
                                JSONObject jsonObject = new JSONObject(jsonString);
                                text_code.setText(jsonObject.getJSONArray("eventos").getJSONObject(0).getString("codigo"));
                                text_desc.setText(jsonObject.getJSONArray("eventos").getJSONObject(0).getString("descricao"));
                                text_city.setText(jsonObject.getJSONArray("eventos").getJSONObject(0).getJSONObject("unidade").getJSONObject("endereco").getString("cidade"));
                                text_uf.setText(jsonObject.getJSONArray("eventos").getJSONObject(0).getJSONObject("unidade").getJSONObject("endereco").getString("uf"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progress.dismiss();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(getApplicationContext(), "Código Inválido", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    });
                } else {
                    progress.dismiss();
                    Toast.makeText(this, "Sem conexão com Internet no momento", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cepActivityButton.setOnClickListener(view -> finish());
    }

    public boolean validate() {
        boolean result = false;
        String code_value = trackCodeInput.getText().toString();
        if (code_value.length() == 0) {
            trackCodeInput.requestFocus();
            trackCodeInput.setError("O Campo não pode estar vazio");
            progress.dismiss();
        } else if (code_value.length() < 13) {
            trackCodeInput.requestFocus();
            trackCodeInput.setError("O código não pode ser inferior a 13 dígitos");
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