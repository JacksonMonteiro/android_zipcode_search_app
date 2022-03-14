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
import com.example.consultacep.service.CepServiceGenerator;
import com.example.consultacep.service.RetrofitService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CepActivity extends AppCompatActivity {
    private TextView street_view, complement_view, district_view, city_view, uf_view;
    private Button consultCepBtn;
    private EditText cepInput;
    Cep serverResponse = new Cep();
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        // Assign Variables
        cepInput = findViewById(R.id.cep_input);
        street_view = findViewById(R.id.street_value);
        complement_view = findViewById(R.id.complement_value);
        district_view = findViewById(R.id.district_value);
        city_view = findViewById(R.id.city_value);
        uf_view = findViewById(R.id.uf_value);
        consultCepBtn = findViewById(R.id.consult_cep_button);

        attachButtonListeners();
    }

    public void attachButtonListeners() {
        consultCepBtn.setOnClickListener(view -> {
            progress = new ProgressDialog(CepActivity.this);
            progress.setTitle("Consultando...");
            progress.show();

            String cep_string = cepInput.getText().toString();

            /*final String json = "{\r\"cep\" : \"" + cep_string + "\"\r}";
            RequestBody objectJson = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json); //Transforma o json em um requestbody*/
            retrofitConverter(cep_string);
        });
    }

    public void setValues() {
        street_view.setText(serverResponse.getResult());
    }

    public void retrofitConverter(String cep) {

        RetrofitService service = CepServiceGenerator.createCepService(RetrofitService.class);

        Call<Cep> call = service.consultCep(cep);

        call.enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful()) {

                    Cep resp = response.body();


                    if (resp != null) {
                        Log.d("resp-body", String.valueOf(resp));
                        Log.i("response", "" + response.body());
                        serverResponse.setResult(resp.getResult());
                        serverResponse.setValid(resp.isValid());

                        progress.dismiss();
                        setValues();
                    } else {
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Resposta não obteve sucesso", Toast.LENGTH_SHORT).show();
                    ResponseBody errorBody = response.errorBody();

                    try {
                        Log.e("Error_Body", errorBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}