package com.example.appwebservicetempo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText edtCidade;
    TextView txtDados;
    Button btnBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCidade = findViewById(R.id.edtCidade);
        txtDados = findViewById(R.id.txtDados);
        btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrevisao();
            }
        });
    }

    private void getPrevisao(){
        Tempo tempo = null;
        String dados = "";

        try {
            tempo = new HttpTempo(edtCidade.getText().toString()).execute().get();

            dados += "Estado atual: " + tempo.weather.get(0).description + "\n";
            dados += "Temperatura: " + String.format("%.1f", tempo.main.temperatura - 273.15) + "ºC\n";
            dados += "Máxima prevista: " + String.format("%.1f", tempo.main.maxima - 273.15) + "ºC\n";
            dados += "Umidade relativa: " + tempo.main.umidade + "%\n";
            dados += "Velocidade do vento: " +  String.format("%.1f", tempo.wind.speed * 1.852) + "km/h";

            txtDados.setText(dados);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}