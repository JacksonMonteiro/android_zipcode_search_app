package com.example.consultacep.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consultacep.R;
import com.example.consultacep.model.ServerResponse;
import com.example.consultacep.service.CepServiceGenerator;
import com.example.consultacep.service.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CepActivity extends AppCompatActivity {
    private TextView street_view, complement_view, district_view, city_view, uf_view;
    private Button consultCpfBtn;
    private EditText cpfInput;
    ServerResponse serverResponse = new ServerResponse();
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        // Assign Variables
        cpfInput = findViewById(R.id.cpf_input);
        street_view = findViewById(R.id.street_value);
        complement_view = findViewById(R.id.complement_value);
        district_view = findViewById(R.id.district_value);
        city_view = findViewById(R.id.city_value);
        uf_view = findViewById(R.id.uf_value);

        attachButtonListeners();
    }

    public void attachButtonListeners() {
        consultCpfBtn.setOnClickListener(view -> {
            progress = new ProgressDialog(getApplicationContext());
            progress.setTitle("Consultando...");
            progress.show();

            String cep_string = cpfInput.getText().toString();

            retrofitConverter(cep_string);
        });
    }

    public void setValues() {
        street_view.setText(serverResponse.getResult());
    }

    public void retrofitConverter(String cep) {
        RetrofitService service = CepServiceGenerator.createCepService(RetrofitService.class);
        Call<ServerResponse> call = service.useService(cep);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    ServerResponse svrResp = response.body();
                    if (svrResp != null) {
                        if (svrResp.isValid()) {
                            serverResponse.setResult(serverResponse.getResult());
                            serverResponse.setValid(serverResponse.isValid());
                            progress.dismiss();
                            setValues();
                        } else {
                            Toast.makeText(getApplicationContext(), "Insira um CEP válido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Resposta não obteve sucesso", Toast.LENGTH_SHORT).show();
                    ResponseBody errorbody = response.errorBody();
                }

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}