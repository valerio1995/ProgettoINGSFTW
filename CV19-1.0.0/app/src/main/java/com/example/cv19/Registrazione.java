package com.example.cv19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

import Mail.Mail;

public class Registrazione extends AppCompatActivity {

    Button buttonAnnulla;
    Button buttonRegistra;
    EditText nome;
    EditText email;
    EditText password;
    EditText confirmpass;
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
        nome = (EditText) findViewById(R.id.editTextNome);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        confirmpass = (EditText) findViewById(R.id.editTextConferma);
        progressDialog = new ProgressDialog(this);
        checkAnonimo = (CheckBox) findViewById(R.id.checkBoxAnonimo);

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
        String namestr = nome.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String confirm = confirmpass.getText().toString();
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
            if(namestr.trim().equals("") || mail.trim().equals("") || pass.trim().equals("") || confirm.trim().equals("")){
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

                            String query = "insert into utenti values (NULL,'"+namestr+"','"+mail+"','"+pass+"',"+anonimo+")";

                            Statement stat = con.createStatement();
                            stat.executeUpdate(query);

                            z="Registrazione avvenuta con successo, ti verrà inviata una mail di conferma";
                            isSuccess = true;

                            //Invio della mail
                            if(Mail.SendMail(mail) == 1){
                                Toast.makeText(Registrazione.this, "Mail inviata con successo!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Registrazione.this, "Invio mail non riuscito...", Toast.LENGTH_SHORT).show();
                            }

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
}