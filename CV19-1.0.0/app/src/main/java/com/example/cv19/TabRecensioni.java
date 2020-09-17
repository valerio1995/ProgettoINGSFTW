package com.example.cv19;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.*;

public class TabRecensioni extends Fragment {
    private static final String TAG = "TabRecensioni";
    Context c;
    TextView sommaTextView;
    TextView mediaTextView;
    String nomeStruttura;
    String ricerca;
    String username;
    Button buttonAggiungiRecensione;
    Button buttonIndietro;
    ConnectionClass connectionClass;
    ResultSet rs;
    LinearLayout valutazione;
    LinearLayout date;
    LinearLayout autore;
    int somma=0;//n

int i=0;
    String testoRecensione;
    String nomeAutore;
    String dataRecensione;
    String titoloRecensione;
    ArrayList<String> listaRecensioni_autore =new ArrayList<>();
    ArrayList<String> listaRecensioni_data = new ArrayList<>();
    ArrayList<String> listaRecensioni_valutazione = new ArrayList<>();
    ArrayList<String> listaRecensioni_testo = new ArrayList<>();
    ArrayList<String> listaRecensioni_titolo = new ArrayList<>();

    public TabRecensioni(String s,String x,String z){
        this.nomeStruttura=s;
        this.username=x;
        this.ricerca=z;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionClass=new ConnectionClass();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recensioni,
                container, false);
        autore = view.findViewById(R.id.l_testo1);
        valutazione = view.findViewById(R.id.l_testo2);
        date = view.findViewById(R.id.l_testo3);
        sommaTextView=view.findViewById(R.id.textTesto2);
        mediaTextView=view.findViewById(R.id.mediaPuntiStruttura);

        creaListaRecensioni();
        calcolaNumeroRecensioni();
        calcolaMediaRecensioni();

        buttonAggiungiRecensione=(Button) view.findViewById(R.id.buttonAggiungiRecensione);

        if(username!=null){

            buttonAggiungiRecensione.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    addRecensione();
                }
            });
        }else{
            buttonAggiungiRecensione.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getContext(), "Devi registrarti per lasciare una recensione!!", Toast.LENGTH_LONG).show();
                }
            });
        }

        buttonIndietro = (Button)view.findViewById(R.id.buttonInd);
        buttonIndietro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ricerca!=null){
                    openRicerca();
                }else{
                    openHome();
                }
            }
        });
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_recensioni, container, false);
        return view;
    }

    private void calcolaMediaRecensioni() {
        String z;
        double media=0;//n
        String m;

        try{
            Connection con = connectionClass.CONN();

            if(con == null){
                z = "Controlla la connessione ad internet...";
            }else{
                String query = "select Sum(valutazione) as media from recensioni where visibile=1 and nomeStruttura= '"+nomeStruttura+"' ";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    media = rs.getInt("media");
                }
                media=media/somma;
                m=String.valueOf(media).toString();
                mediaTextView.setText("Punteggio medio: "+m.substring(0,3));
            }
            z=" avvenuta con successo";
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
            e.getCause();
        }
    }

    private void calcolaNumeroRecensioni() {
        String z;

        String m;

        try{
            Connection con = connectionClass.CONN();

            if(con == null){
                z = "Controlla la connessione ad internet...";
            }else{
                String query = "select count(*) as somma from recensioni where visibile=1 and nomeStruttura= '"+nomeStruttura+"' ";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    somma = rs.getInt("somma");
                }
                m= String.valueOf(somma).toString();
                sommaTextView.setText("Numero recensioni: "+m);
                }
                z=" avvenuta con successo";
            } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
            e.getCause();
        }
    }

    public void addRecensione(){
        Intent i = new Intent(this.getContext(),AggiungiRecensione.class);
        i.putExtra("nomeStruttura", nomeStruttura);
        i.putExtra("username", username);
        startActivity(i);
    }

    public void openRicerca(){
        Intent i = new Intent(this.getContext(),Ricerca.class);
        i.putExtra("ricerca", ricerca);
        i.putExtra("username", username);
        startActivity(i);
    }

    public void openHome(){
        Intent i = new Intent(this.getContext(),Home.class);
        i.putExtra("username", username);
        startActivity(i);
    }

    public void creaListaRecensioni(){
        String z;
        int dim = 0;
        try{
            Connection con = connectionClass.CONN();
            //controlli sulla connessione
            if(con == null){
                z = "Controlla la connessione ad internet...";
            }else{
                String query = "select * from recensioni where visibile=1 and nomeStruttura= '"+nomeStruttura+"' ";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    testoRecensione = rs.getString("testo");
                    dataRecensione = rs.getString("data");
                    nomeAutore=rs.getString("autore");
                    titoloRecensione=rs.getString("titolo");
                    //data=rs.getDate("data");
                    int valuta = rs.getInt("valutazione");
                    //converto 'valuta' a stringa
                    String valutazione= Integer.toString(valuta);

                    listaRecensioni_titolo.add(titoloRecensione);
                    listaRecensioni_valutazione.add(valutazione);
                    listaRecensioni_autore.add(nomeAutore);
                    listaRecensioni_testo.add(testoRecensione);
                    listaRecensioni_data.add(dataRecensione);

                    dim++;
                }

                final LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                for(i=0; i < dim; i++){
                    final TextView testo= new TextView(this.getContext());
                    testo.setText(listaRecensioni_titolo.get(i));

                    testo.setLayoutParams(lp2);
                    autore.addView(testo);

                    final TextView testo2= new TextView(this.getContext());
                    testo2.setText(listaRecensioni_valutazione.get(i));

                    testo2.setLayoutParams(lp2);
                    valutazione.addView(testo2);

                    final TextView testo3= new TextView(this.getContext());
                    testo3.setText(listaRecensioni_autore.get(i));


                    testo3.setLayoutParams(lp2);
                    date.addView(testo3);

                    final TextView testo4= new TextView(this.getContext());
                    testo4.setText(listaRecensioni_testo.get(i));

                    final TextView testo5= new TextView(this.getContext());
                    testo5.setText(listaRecensioni_data.get(i));



                    //Rendo il testo cliccabile
                    testo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(TabRecensioni.this.getContext(), Recensione.class);
                            //passo la recensione alla pagina successiva
                            testoRecensione=testo4.getText().toString();
                            titoloRecensione=testo.getText().toString();
                            dataRecensione=testo5.getText().toString();
                            String autore=testo3.getText().toString();
                            String valutazione=testo2.getText().toString();
                            intent.putExtra("testo", testoRecensione);
                            intent.putExtra("titolo", titoloRecensione);
                            intent.putExtra("struttura", nomeStruttura);
                            intent.putExtra("username", username);
                            intent.putExtra("ricerca",ricerca);
                            intent.putExtra("valutazione",valutazione);
                            intent.putExtra("autore",autore);
                            intent.putExtra("data",dataRecensione);



                            startActivity(intent);
                        }
                    });

                }
                z=" avvenuta con successo";
            }
        }catch (Exception ex){
            z = "Exception: " + ex;
            Log.d(TAG, " exception mess:"+ex.getMessage());
            Log.d(TAG, " exception mess:"+ex.getCause());
        }
    }
}
