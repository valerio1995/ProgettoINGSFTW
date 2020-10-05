package com.example.cv19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;



public class AggiungiRecensione extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "AggiungiRecensione";
    private Spinner spinner;
    String valutazione;
    String username;
    String nomeStruttura;
    String descrizione;
    String indirizzo;
    String numeroTelefonico;
    String ricerca;
    Button bottoneInvia;
    Button chiudi;
    EditText testo;
    EditText title;
    ConnectionClass connectionClass;
    ResultSet rs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_recensione);

        getSupportActionBar().hide();

        bottoneInvia = findViewById(R.id.buttonInvia);
        testo = findViewById(R.id.textRecensione);
        title = findViewById(R.id.titoloRecensioneAdd);
        username = getIntent().getStringExtra("username");
        nomeStruttura = getIntent().getStringExtra("nomeStruttura");
        descrizione=getIntent().getStringExtra("descrizione");
        indirizzo=getIntent().getStringExtra("indirizzo");
        numeroTelefonico=getIntent().getStringExtra("numero");
        ricerca=getIntent().getStringExtra("ricerca");
        spinner = findViewById(R.id.spinnerPunteggio);
        connectionClass = new ConnectionClass();
        chiudi = findViewById(R.id.buttonChiudi);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Punteggio, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        bottoneInvia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AggiungiRecensione.inviaRecensione invia = new AggiungiRecensione.inviaRecensione();
                invia.execute("");
                openDettagli();
            }
        });

        chiudi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDettagli();
            }
        });
        }

        public void openDettagli(){
            Intent i = new Intent(this, PaginaStruttura.class);
            i.putExtra("username", username);
            i.putExtra("nomeStruttura", nomeStruttura);
            i.putExtra("descrizione", descrizione);
            i.putExtra("indirizzo",indirizzo);
            i.putExtra("numero", numeroTelefonico);
            i.putExtra("ricerca", ricerca);

            startActivity(i);
        }

    @Override

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        valutazione = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public int Anonimato(String username){
        int anonimato=0;
        try{
            Connection con = connectionClass.CONN();
            //controlli sulla connessione
            if(con == null){
                Log.d(TAG, "Controlla la connessione ad internet");
            }else{
                String query = "select * from utenti where user= '"+username+"'";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    anonimato = rs.getInt("anonimo");
                }
            }
        }catch (Exception ex){
            Log.d(TAG, " exception mess:"+ex.getMessage());
            Log.d(TAG, " exception mess:"+ex.getCause());
        }

        return anonimato;
    }

    public String takeName(String username){
        String nome = null;
        try{
            Connection con = connectionClass.CONN();
            //controlli sulla connessione
            if(con == null){
                Log.d(TAG, "Controlla la connessione ad internet");
            }else{
                String query = "select * from utenti where user= '"+username+"'";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    nome = rs.getString("nome");
                }
            }
        }catch (Exception ex){
            Log.d(TAG, " exception mess:"+ex.getMessage());
            Log.d(TAG, " exception mess:"+ex.getCause());
        }

        return nome;
    }

    public String takeSurname(String username){
        String cognome = null;
        try{
            Connection con = connectionClass.CONN();
            //controlli sulla connessione
            if(con == null){
                Log.d(TAG, "Controlla la connessione ad internet");
            }else{
                String query = "select * from utenti where user= '"+username+"'";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    cognome = rs.getString("cognome");
                }
            }
        }catch (Exception ex){
            Log.d(TAG, " exception mess:"+ex.getMessage());
            Log.d(TAG, " exception mess:"+ex.getCause());
        }

        return cognome;
    }


    public class inviaRecensione extends AsyncTask<String,String,String> {

        //Covnerto a stringa
        String testoRecensione = testo.getText().toString();
        String titolo = title.getText().toString();
        String z = "";
        @Override
        protected void onPreExecute(){
        }

        @Override
        protected String doInBackground(String... params){

            //Controllo se i campi sono vuoti
            if(testoRecensione.trim().equals("") || titolo.trim().equals("")){
                z = "Riempi tutti i campi...";
            }else{
                try{
                    Log.d(TAG, "DENTRO ");
                    Connection con = connectionClass.CONN();

                    //controlli sulla connessione
                    if(con == null){
                        z = "Controlla la connessione ad internet...";
                    }else{
                        //Creo l'oggetto data
                        Date data = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                        //Converto la data a stringa
                        String strData = sdf.format(data);

                        if(Anonimato(username) != 1){
                            String query = "insert into recensioni values (NULL,'"+titolo+"','"+nomeStruttura+"','"+testoRecensione+"',"+valutazione+",'"+takeName(username)+" "+takeSurname(username)+"','"+strData+"',"+0+")";

                            Log.d(TAG, "FATTA ");
                            Statement stat = con.createStatement();
                            stat.executeUpdate(query);
                        }else{
                            String query = "insert into recensioni values (NULL,'"+titolo+"','"+nomeStruttura+"','"+testoRecensione+"',"+valutazione+",'"+username+"','"+strData+"',"+0+")";

                            Log.d(TAG, "FATTA ");
                            Statement stat = con.createStatement();
                            stat.executeUpdate(query);
                        }

                        z="Recensione inviata con successo ed in attesa di conferma da parte dello staff. Riceverai una mail di vconferma nel momento della pubblicazione.";
                    }
                }catch (Exception ex){
                    z = "Exception: " + ex;
                    Log.d(TAG, "Exception: "+ex);
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String z){
            Toast.makeText(getBaseContext(), ""+z, Toast.LENGTH_LONG).show();

        }
    }
}
