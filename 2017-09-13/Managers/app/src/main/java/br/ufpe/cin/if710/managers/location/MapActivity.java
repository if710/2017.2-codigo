package br.ufpe.cin.if710.managers.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.ufpe.cin.if710.managers.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    public final static String LAT_KEY = "latitude";
    public final static String LON_KEY = "longitude";

    private double latitude;
    private double longitude;

    private GoogleMap gMap;
    //nao estou checando que tem acesso ao play services como no caso de FusedLocation...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent i = getIntent();
        latitude  = i.getDoubleExtra(LAT_KEY,-8.047);
        longitude = i.getDoubleExtra(LON_KEY,-34.876);

        SupportMapFragment mapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapa.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng location = new LatLng(latitude, longitude);
        gMap.addMarker(new MarkerOptions().position(location).title("Marcador!"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
