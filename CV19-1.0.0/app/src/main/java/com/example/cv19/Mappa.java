package com.example.cv19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Mappa extends AppCompatActivity {
    private static final String TAG = "MappaAcitivity";

    SupportMapFragment mapFragment;
    String username;
    FusedLocationProviderClient client;
    ConnectionClass connectionClass;
    ResultSet rs;
    ArrayList<Double> lista_latitudine = new ArrayList<>();
    ArrayList<Double> lista_longitudine = new ArrayList<>();
    ArrayList<String> lista_nomeStruttura = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        connectionClass = new ConnectionClass();
        username = getIntent().getStringExtra("username");
        Log.d(TAG, "1");
        client = LocationServices.getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else{
            //Permessi negati
            ActivityCompat.requestPermissions(Mappa.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private <TAG> void AddMarkerStruct(GoogleMap googleMap){
        int dim=0;
        double latitudine,longitudine;
        String nomeStruttura;
        Log.d(TAG, "2");

        try{
            Connection con = connectionClass.CONN();
            //controlli sulla connessione
            if(con == null){
                Log.d(TAG, "Controlla la connessione ");
            }else{
                String query = "select * from strutture";
                Statement stat = con.createStatement();
                rs=stat.executeQuery(query);
                while(rs.next()){
                    latitudine = rs.getDouble("latitudine");
                    longitudine = rs.getDouble("longitudine");
                    nomeStruttura = rs.getString("nome");
                    lista_latitudine.add(latitudine);
                    lista_longitudine.add(longitudine);
                    lista_nomeStruttura.add(nomeStruttura);
                    dim++;
                }

                final LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                for(int i=0; i < dim+1; i++){
                    Log.d(TAG, "1.Indice: "+i);
                    LatLng struttura = new LatLng(lista_latitudine.get(i), lista_longitudine.get(i));
                    //Crea marker di posizione
                    MarkerOptions other = new MarkerOptions().position(struttura).title(lista_nomeStruttura.get(i));
                    //Aggiungi marker su mappa
                    googleMap.addMarker(other);


                    Log.d(TAG, "ripeto ");

                    /*Rendo il marker cliccabile*/
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

                        int clicker = 0;
                            @Override
                        public boolean onMarkerClick(Marker marker){
                                clicker = clicker + 1;
                                if(marker.getTitle().compareTo("I am here") != 0 && clicker == 2 ){
                                    clicker = 0;
                                    Intent intent = new Intent(Mappa.this, PaginaStruttura.class);
                                    //passo il nome della struttura
                                    intent.putExtra("username", username);
                                    intent.putExtra("nomeStruttura", marker.getTitle());
                                    startActivity(intent);
                                }
                                if(clicker >= 2){
                                    clicker = 0;
                                }
                                return false;
                        }
                    });
                }
            }
        }catch (Exception ex){
            Log.d(TAG, "Exception: "+ex.getMessage());
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location != null){
                    //Sync map
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Inizializza lat lng

                            LatLng latLng = new LatLng(40.829768,14.194956);

                            AddMarkerStruct(googleMap);


                            //Crea marker di posizione
                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am here");

                            //Zoom mappa
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.2f));

                            //Aggiungi marker su mappa
                            googleMap.addMarker(options).showInfoWindow();



                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Permessi abiliitati
                getCurrentLocation();
            }
        }
    }
}