package com.example.cv19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button bottoneLogin;
    ProgressBar progressBar;
    Button buttonAnnulla;
    ConnectionClass connectionClass;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        //Ottengo la stringa data in input dall'utente
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        bottoneLogin = (Button) findViewById(R.id.buttonLoggo);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        buttonAnnulla = (Button) findViewById(R.id.buttonAnnulla);

        connectionClass = new ConnectionClass();

        //Mostriamo messaggi fin quando carica
        progressBar.setVisibility(View.GONE);

        buttonAnnulla.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMain();
            }
        });


        bottoneLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }

        });
    }

    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public class CheckLogin extends AsyncTask<String,String,String>{

        //Rendo i parametri in formato stringa
        String namestr = username.getText().toString();
        String passstr = password.getText().toString();
        String z = "";

        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            if(namestr.trim().equals("") || passstr.trim().equals("")){
                z = "Riempire tutti i campi...";
            }else{
                try {
                    //connessione al server
                    Connection con = connectionClass.CONN();
                    if(con == null){
                        z = "Controlla la tua connessione ad internet";
                    }else{
                        String query = "select * from utenti where user='" +namestr+ "' and pass = '" +passstr+"'";
                        Statement stat = con.createStatement();
                        ResultSet rs = stat.executeQuery(query);
                        if(rs.next()){
                            z = "Login effettuato!";
                            isSuccess = true;
                            con.close();
                        }else{
                            z = "Credenziali non valide!";
                            isSuccess = false;
                        }
                    }
                }catch (Exception ex){
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Login.this,z,Toast.LENGTH_SHORT).show();
            if(isSuccess){
                Toast.makeText(getBaseContext(), "LOGIN", Toast.LENGTH_LONG).show();
                openHome();
            }
        }
    }

    public void openHome(){
        Intent i = new Intent(this, Home.class);
        i.putExtra("username", username.getText().toString());
        startActivity(i);
    }
}