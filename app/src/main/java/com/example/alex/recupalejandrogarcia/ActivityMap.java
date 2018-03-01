package com.example.alex.recupalejandrogarcia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Alex on 01/03/2018.
 */

public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mapa;
    SupportMapFragment fragmentMapa;
    String localidad;
    String latitud;
    String longitud;
    String codPais;
    LatLng posicion;
    MarkerOptions marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        fragmentMapa= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragmentMapa.getMapAsync(this);

        localidad = (String) getIntent().getExtras().getSerializable("localidad");
        latitud = (String) getIntent().getExtras().getSerializable("latitud");
        longitud = (String) getIntent().getExtras().getSerializable("longitud");
        codPais = (String) getIntent().getExtras().getSerializable("codPais");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa=googleMap;
        mapa.setMapType(googleMap.MAP_TYPE_NORMAL);
        mapa.getUiSettings().setZoomControlsEnabled(true);


        double lat=Double.parseDouble(latitud);
        double lon =Double.parseDouble(longitud);


        posicion=new LatLng(lat, lon);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 2));
        if(codPais.equals("ES")){
            marcador = new MarkerOptions().title(localidad).position(posicion).title(localidad).snippet(codPais);
            marcador.icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador_es));
            mapa.addMarker(marcador);
        }
        else {
            marcador = new MarkerOptions().title(localidad).position(posicion).title(localidad).snippet(codPais);
            mapa.addMarker(marcador);
        }
    }

}
