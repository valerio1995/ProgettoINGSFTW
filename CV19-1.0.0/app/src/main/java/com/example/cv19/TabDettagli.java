package com.example.cv19;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class TabDettagli extends Fragment {
    private static final String TAG = "TabDettagli";
    Button buttonIndietro;
    String ricerca;
    String username;
    ImageView immagine;

    public TabDettagli(String r,String u){
            this.ricerca=r;
            this.username=u;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dettagli,
                container, false);

        immagine= view.findViewById(R.id.imageView23);

        immagine.setImageResource(R.drawable.hotel_lucia);
        buttonIndietro = (Button)view.findViewById(R.id.buttonIndietro23);
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

        return view;
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
}
