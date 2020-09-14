package com.example.cv19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Recensione extends AppCompatActivity {

    String testoRecensione;
    String nomeStruttura;
    String username;
    String ricerca;
    String titoloRecensione;
    Button bottoneInd;
    TextView testo;
    TextView titolo;

    private static final String TAG = "Recensione";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensione);

        titoloRecensione=getIntent().getStringExtra("titolo");
        testoRecensione = getIntent().getStringExtra("testo");
        nomeStruttura = getIntent().getStringExtra("struttura");
        username=getIntent().getStringExtra("username");
        ricerca=getIntent().getStringExtra("ricerca");
        testo = findViewById(R.id.testoRecensione);
        titolo = findViewById(R.id.titoloRecensione1);
        bottoneInd = findViewById(R.id.buttonIndietro);
        titolo.setText(titoloRecensione);
        testo.setText(testoRecensione);

        bottoneInd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDettagli();
            }
        });
    }

    public void openDettagli(){
        Intent intent = new Intent(this, PaginaStruttura.class);
        intent.putExtra("nomeStruttura", nomeStruttura);
        intent.putExtra("username", username);
        intent.putExtra("ricerca", ricerca);
        startActivity(intent);

    }

}