package com.example.cv19;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    Button bottoneMap;
    Button buttonLogout;
    Button buttonCerca;
    EditText barra;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        username = getIntent().getStringExtra("username");
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonCerca = (Button) findViewById(R.id.bottoneCerca);
        barra = (EditText) findViewById(R.id.barraRicerca);
        bottoneMap = findViewById(R.id.buttonMappa);

        buttonLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMain();
            }
        });
        
        buttonCerca.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSearch();
            }
        });

        bottoneMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMap();
            }
        });

    }

    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }

    public void openMap(){
        Intent intent = new Intent(this, Mappa.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
    public void openSearch(){

        Intent intent = new Intent(this, Ricerca.class);
        intent.putExtra("ricerca", barra.getText().toString());
        intent.putExtra("username", username);
        startActivity(intent);

    }
}