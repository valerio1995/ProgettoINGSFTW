package com.example.cv19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Recensione extends AppCompatActivity {

    String testoRecensione;
    String nomeStruttura;
    String username;
    String ricerca;
    String titoloRecensione;
    String dataRecensione;
    String autore;
    String valutazione;
    Button bottoneInd;
    TextView testoTextView;
    TextView titoloTextView;
    TextView autoreTextView;
    TextView dataTextView;
    TextView valutazioneTextView;
    TextView nomeStrutturaTextView;

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
        valutazione=getIntent().getStringExtra("valutazione");
        autore=getIntent().getStringExtra("autore");
        dataRecensione=getIntent().getStringExtra("data");


        testoTextView = findViewById(R.id.testoRecensione);
        titoloTextView = findViewById(R.id.titoloRecensione1);
        bottoneInd = findViewById(R.id.buttonIndietro);
        valutazioneTextView=findViewById(R.id.testoValutazione);
        autoreTextView=findViewById(R.id.nomeAutore2);
        dataTextView=findViewById(R.id.dataRecensione21);
        nomeStrutturaTextView=findViewById(R.id.nomeStruttura2);

        nomeStrutturaTextView.setText(nomeStruttura);
        titoloTextView.setText(titoloRecensione);
        testoTextView.setText(testoRecensione);
        dataTextView.setText("Data: "+dataRecensione);
        valutazioneTextView.setText("Valutazione: "+valutazione+" su 5.");
        autoreTextView.setText("Autore: "+autore);


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