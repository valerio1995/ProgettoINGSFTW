package com.example.cv19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class Registrazione extends AppCompatActivity {

    Button buttonAnnulla;
    Button buttonRegistra;
    EditText nome;
    EditText email;
    EditText password;
    EditText confirmpass;

    //
    EditText username;
    EditText cognome;

    ConnectionClass connectionClass;
    ProgressDialog progressDialog;
    CheckBox checkAnonimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        buttonAnnulla = (Button) findViewById(R.id.ButtonAnnulla);
        buttonRegistra = (Button) findViewById(R.id.ButtonRegistra);
        nome = (EditText) findViewById(R.id.editTextNome11);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        confirmpass = (EditText) findViewById(R.id.editTextConferma);
        progressDialog = new ProgressDialog(this);
        checkAnonimo = (CheckBox) findViewById(R.id.checkBoxAnonimo);

//
        username = (EditText) findViewById(R.id.editTextUsername2);
        nome = (EditText) findViewById(R.id.editTextNome11);
        cognome = (EditText) findViewById(R.id.editTextCognome);


        connectionClass = new ConnectionClass();
        buttonAnnulla.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMain();
            }
        });

        buttonRegistra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Registra reg = new Registra();
                reg.execute("");
            }
        });
    }

    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public class Registra extends AsyncTask<String,String,String>{

        //Covnerto a stringa
        String nomestr = nome.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String confirm = confirmpass.getText().toString();

        //
        String userstr = username.getText().toString();
        String cognomestr = cognome.getText().toString();


        String z = "";
        int anonimo = 0;
        boolean isSuccess=false;

        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Caricamento...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params){

            //Controllo se i campi sono vuoti
            if(userstr.trim().equals("") || mail.trim().equals("") || pass.trim().equals("") || confirm.trim().equals("")){
                z = "Riempi tutti i campi...";
            }else{

                if(pass.equals(confirm)){
                    try{
                        Connection con = connectionClass.CONN();

                        //controlli sulla connessione
                        if(con == null){
                            z = "Controlla la connessione ad internet...";
                        }else{
                            //Imposto l'anonimato
                            if(checkAnonimo.isChecked()){
                                anonimo = 1;
                            }

                            //Rendiamo il nome e cognome "normale"
                            nomestr = normalizza_parola(nomestr);
                            cognomestr = normalizza_parola(cognomestr);

                            String query = "insert into utenti values (NULL, '"+ nomestr +"', '"+ cognomestr +"','"+ userstr +"','"+mail+"','"+pass+"',"+anonimo+")";

                            Statement stat = con.createStatement();
                            stat.executeUpdate(query);

                            z="Registrazione avvenuta con successo!";
                            isSuccess = true;

                            openMain();

                        }
                    }catch (Exception ex){
                        isSuccess = false;
                        z = "Exception: " + ex;
                    }
                }else{
                    z = "Ripetere la conferma password...";
                }
            }

            return z;
        }

        @Override
        protected void onPostExecute(String z){
            Toast.makeText(getBaseContext(), ""+z, Toast.LENGTH_LONG).show();
            progressDialog.hide();
        }
    }

    public String normalizza_parola(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}