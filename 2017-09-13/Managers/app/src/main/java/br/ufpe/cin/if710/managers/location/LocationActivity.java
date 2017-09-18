package br.ufpe.cin.if710.managers.location;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LocationActivity extends ListActivity implements LocationListener {
    private LocationManager mgr;
    ArrayList<Location> data = new ArrayList<>();
    ArrayAdapter<Location> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_location);
        mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        adapter=new ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

    //evita warnings do Android Studio (checagem sintática apenas)
    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onResume() {
        super.onResume();
        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        //especificando GPS_PROVIDER
        //atualizacoes de hora em hora
        //o dispositivo deve ter se movido em pelo menos 1km
        //objeto LocationListener
        //mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3600000, 1000, this);

        //se colocar 0,0 tenta o maximo de updates
        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);

        //pode usar com PendingIntent, para disparar um broadcast por ex.
        //addProximityAlert()
        //}
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //so recebe updates enquanto visivel
            mgr.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        adapter.add(location);
        //location.getSpeed();
        //location.getAccuracy();
        //location.getLatitude();
        //location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //nao utilizado neste exemplo
    }

    @Override
    public void onProviderEnabled(String provider) {
        //nao utilizado neste exemplo
    }

    @Override
    public void onProviderDisabled(String provider) {
        //nao utilizado neste exemplo
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Location loc = (Location) l.getAdapter().getItem(position);
        Toast.makeText(this, loc.getLatitude() + " " + loc.getLongitude(), Toast.LENGTH_SHORT).show();

        //simplesmente centraliza o mapa na latitude/longitude escolhida
        //nao amarra com google maps
        String locData = "geo:"+loc.getLatitude()+","+loc.getLongitude();

        //locData = "geo:"+loc.getLatitude()+","+loc.getLongitude()+"(AQUI)";

        //abre streetview
        //locData = "google.streetview:cbll="+loc.getLatitude()+","+loc.getLongitude();

        //abre navigation
        //locData = "google.navigation:q="+loc.getLatitude()+","+loc.getLongitude();

        Uri locationURI = Uri.parse(locData);

        Intent i = new Intent(Intent.ACTION_VIEW, locationURI);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }
}
