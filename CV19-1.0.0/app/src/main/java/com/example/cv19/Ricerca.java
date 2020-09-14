package com.example.cv19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Ricerca extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "Ricerca";
    TextView tv;
    ConnectionClass connectionClass;
    ResultSet rs;
    String username;
    String ricerca;
    String nomeHotel="";
    String prezzoHotel="";
    LinearLayout lv;
    Button cerca;
    Button logout;
    ArrayList<String> listaStrutture_prezzo =new ArrayList<>();
    ArrayList<String> listaStrutture_nome = new ArrayList<>();
    ArrayList<TextView> lista = new ArrayList<>();
    LinearLayout lText1;
    LinearLayout lText2;

    Spinner spinnerFiltri;
    String filtro;
    Button ordina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        getSupportActionBar().hide();

        ricerca = getIntent().getStringExtra("ricerca");
        username = getIntent().getStringExtra("username");

        lv=(LinearLayout)findViewById(R.id.tabella);
        tv = (TextView) findViewById(R.id.textView44);
        tv.setText(ricerca);
        lText1 = findViewById(R.id.l_text1);
        lText2= findViewById(R.id.l_text2);
        cerca = findViewById(R.id.buttonIndietro1);
        logout = findViewById(R.id.buttonLogoutRicerca);
        connectionClass = new ConnectionClass();
        creaLista();

        ordina = findViewById(R.id.buttonOrdina);
        spinnerFiltri = findViewById(R.id.spinnerFiltri);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Filtri, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltri.setAdapter(adapter);
        spinnerFiltri.setOnItemSelectedListener(this);
        ordina.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(filtro.trim().equals("nome") || filtro.trim().equals("tariffa")){
                    Log.d(TAG, "Cliccato ORDINA ");
                    ordinaElementi();
                }
            }
        });



        if(username == null){
            logout.setVisibility(View.INVISIBLE);
        }

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMain();
            }
        });

        cerca.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(username == null){
                    openMain();
                }else{
                    openHome();
                }
            }
        });
    }
    public void clearList(){
        lText1.removeAllViews();
        lText2.removeAllViews();
        listaStrutture_nome.clear();
        listaStrutture_prezzo.clear();
    }
    public int createList(ArrayList<String> lista1,LinearLayout ll){
        int isSuccess=0;

        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for(int i = 0 ; i < lista1.size(); i++){
            final TextView tmp = new TextView(Ricerca.this);
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tmp.setText(lista1.get(i));
            tmp.setLayoutParams(lp);
            ll.addView(tmp);

            if(i==lista1.size()-1){
                isSuccess=1;
            }
        }

        return isSuccess;
    }
    public int ordinaElementi(){
        String z;
        int dim = 0;
        int isSuccess = 0;
        try{
            Connection con = connectionClass.CONN();
            //controlli sulla connessione
            if(con == null){
                isSuccess = 0;
            }else{

                clearList();

                String query = "select * from strutture where citta= '"+ricerca+"' order by "+filtro+"";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    nomeHotel=rs.getString("nome");
                    prezzoHotel=rs.getString("tariffa")+"€";
                    listaStrutture_nome.add(nomeHotel);
                    listaStrutture_prezzo.add(prezzoHotel);
                    dim++;
                }

                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                for(int i=0; i < dim; i++){
                    Log.d(TAG, "listaStrtture_nome =  "+listaStrutture_nome.get(i));
                    Log.d(TAG, "listaStrtture_prezzo =  "+listaStrutture_prezzo.get(i));

                    final TextView testo= new TextView(this);
                    testo.setText(listaStrutture_nome.get(i));

                    testo.setLayoutParams(lp);
                    lText1.addView(testo);

                    final TextView testo2= new TextView(this);
                    testo2.setText(listaStrutture_prezzo.get(i));

                    testo2.setLayoutParams(lp);
                    lText2.addView(testo2);

                    /*Rendo il testo cliccabile*/
                    testo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Ricerca.this, PaginaStruttura.class);
                            String name = testo.getText().toString();
                            intent.putExtra("username", username);
                            intent.putExtra("nomeStruttura", name);
                            intent.putExtra("ricerca", ricerca);
                            startActivity(intent);
                        }
                    });
                }
                isSuccess = 1;
            }
        }catch (Exception ex){
            z = "Exception: " + ex;
            isSuccess = 0;
        }

        return isSuccess;
    }



    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        Log.d(TAG,"RICERCA openmain");
        startActivity(intent);
    }

    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        Log.d(TAG,"RICERCA  openhome");
        startActivity(intent);

    }
    /*
    public int creaLista(){
        int isSuccess1=0;
        int isSuccess2=0;

        String z;
        try{
            Connection con = connectionClass.CONN();
            //controlli sulla connessione
            if(con == null){
                z = "Controlla la connessione ad internet...";
            }else{
                String query = "select * from strutture where citta= '"+ricerca+"' ";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    nomeHotel=rs.getString("nome");
                    prezzoHotel=rs.getString("tariffa")+"€";
                    listaStrutture_nome.add(nomeHotel);
                    listaStrutture_prezzo.add(prezzoHotel);
                }

                isSuccess1=createList(listaStrutture_nome,lText1);
                isSuccess2=createList(listaStrutture_prezzo,lText2);

                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if(isSuccess1 == 1 && isSuccess2==1){
                    return 1;
                }else{
                    return 0;
                }
            }
        }catch (Exception ex){
            return 0;
        }

        return 0;
    }*/

    public void creaLista(){
        String z;
        int dim = 0;
        try{
            Connection con = connectionClass.CONN();
            //controlli sulla connessione
            if(con == null){
                z = "Controlla la connessione ad internet...";
            }else{
                String query = "select * from strutture where citta= '"+ricerca+"' ";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    nomeHotel=rs.getString("nome");
                    prezzoHotel=rs.getString("tariffa")+"€";
                    listaStrutture_nome.add(nomeHotel);
                    listaStrutture_prezzo.add(prezzoHotel);
                     dim++;
                }

                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                for(int i=0; i < dim+1; i++){
                    final TextView testo= new TextView(this);
                    testo.setText(listaStrutture_nome.get(i));

                    testo.setLayoutParams(lp);
                    lText1.addView(testo);

                    final TextView testo2= new TextView(this);
                    testo2.setText(listaStrutture_prezzo.get(i));

                    testo2.setLayoutParams(lp);
                    lText2.addView(testo2);


                    testo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Ricerca.this, PaginaStruttura.class);
                            String name = testo.getText().toString();
                            intent.putExtra("username", username);
                            intent.putExtra("nomeStruttura", name);
                            intent.putExtra("ricerca", ricerca);

                            startActivity(intent);
                        }
                    });
                }
                z=" avvenuta con successo";
            }
        }catch (Exception ex){
            z = "Exception: " + ex;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        filtro = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}