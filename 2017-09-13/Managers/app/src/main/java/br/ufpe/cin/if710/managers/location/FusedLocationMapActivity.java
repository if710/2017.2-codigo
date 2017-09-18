package br.ufpe.cin.if710.managers.location;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;

public class FusedLocationMapActivity extends ListActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>,
        LocationListener {

    private GoogleApiClient playServices;

    ArrayList<Location> data = new ArrayList<Location>();
    ArrayAdapter<Location> adapter;

    private LocationRequest locationRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //configurando a API que vamos utilizar, no caso LOCATION
        playServices = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (playServices!=null) {
            playServices.connect();
        }
    }

    @Override
    protected void onStop() {
        if (playServices!=null) {
            playServices.disconnect();
        }

        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(playServices, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Toast.makeText(this, "Algum problema de conexao... "+result.getErrorMessage(), Toast.LENGTH_LONG).show();
        finish();
    }

    //ao ter acesso a API de Location, o que faremos
    @Override
    public void onConnected(Bundle bundle) {

        //inicializa list view
        adapter=new ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);

        //define requisicao para obter localizacao
        //objeto define quantos updates serao necessarios
        //deadline para desistir se nao conseguir obter location
        //intervalo
        //otimizacao de energia, caso aplicavel
        locationRequest = new LocationRequest()
                .setNumUpdates(5)
                .setExpirationDuration(60000)
                .setInterval(1000)
                .setPriority(LocationRequest.PRIORITY_LOW_POWER);


        LocationSettingsRequest.Builder b = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(playServices, b.build());
        result.setResultCallback(this);
    }


    //se houver algum problema, ou algo do tipo...
    @Override
    public void onConnectionSuspended(int i) {
        Log.w(((Object)this).getClass().getSimpleName(), "onConnectionSuspended() foi executado");
        Toast.makeText(this, "Algum problema de conexao suspensa... "+i, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Location loc = (Location) l.getAdapter().getItem(position);
        Toast.makeText(this, loc.getLatitude() + " " + loc.getLongitude(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent (this, MapActivity.class);
        i.putExtra(MapActivity.LAT_KEY,loc.getLatitude());
        i.putExtra(MapActivity.LON_KEY,loc.getLongitude());
        startActivity(i);
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        int statusCode = locationSettingsResult.getStatus().getStatusCode();
        if (statusCode == LocationSettingsStatusCodes.SUCCESS) {
            PendingResult<Status> result = LocationServices.FusedLocationApi.requestLocationUpdates(playServices, locationRequest, this);

            result.setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "Pedido esta na fila", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao solicitar location! "+status.getStatusMessage(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });

        }
        else if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
            //obter permissao para usar location
        }
        else {
            Toast.makeText(this, "Algum problema ao tentar obter location", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        adapter.add(location);
    }
}
