package com.example.cv19;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonLoginOpen;
    Button bottoneMappa;
    Button buttonSignup;
    Button buttonCerca;
    String username = null;
    EditText barra;
    private static final String TAG = "MainAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        buttonLoginOpen = (Button) findViewById(R.id.buttonLogin);
        buttonSignup = (Button) findViewById(R.id.buttonRegistra);
        buttonCerca = (Button) findViewById(R.id.buttonCerca01);
        barra = (EditText) findViewById(R.id.barra);
        bottoneMappa = findViewById(R.id.buttonMappa);

        buttonLoginOpen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openLogin();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSignup();
            }
        });

        buttonCerca.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSearch();
            }
        });

        bottoneMappa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMap();
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openSignup(){
        Intent intent = new Intent(this, Registrazione.class);
        startActivity(intent);
    }

    public void openMap(){
        Intent intent = new Intent(this, Mappa.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }



    public void openSearch(){

        Intent intent = new Intent(this, Ricerca.class);
        intent.putExtra("ricerca", barra.getText().toString());
        intent.putExtra("username", username);
        startActivity(intent);

    }
}