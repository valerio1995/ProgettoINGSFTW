package com.example.cv19;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PaginaStruttura extends AppCompatActivity {
    private static final String TAG = "PaginaStruttura";
    TextView testo;
    ViewPager viewPager;
    TabDettagli tabDettagli;
    TabRecensioni tabRecensioni;

    String nomeStruttura;
    String username;
    String ricerca;
    ConnectionClass connectionClass;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_struttura);

        nomeStruttura = getIntent().getStringExtra("nomeStruttura");
        username = getIntent().getStringExtra("username");
        ricerca= getIntent().getStringExtra("ricerca");
        testo = (TextView)findViewById(R.id.nomeStruttura);
        testo.setText(nomeStruttura);

        viewPager = findViewById(R.id.view_pager);
        connectionClass = new ConnectionClass();
        tabRecensioni = new TabRecensioni(nomeStruttura,username,ricerca);
        tabDettagli = new TabDettagli();
        PaginaStruttura.MyPagerAdapter myPagerAdapter = new PaginaStruttura.MyPagerAdapter(getSupportFragmentManager(),1);
        viewPager.setAdapter(myPagerAdapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] fragmentNames = {"DETTAGLI", "RECENSIONI"};

        public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return tabDettagli;
                case 1:
                    return tabRecensioni;
            }
            return null;
        }

        @Override
        public int getCount() {
            return fragmentNames.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNames[position];
        }
    }
}
