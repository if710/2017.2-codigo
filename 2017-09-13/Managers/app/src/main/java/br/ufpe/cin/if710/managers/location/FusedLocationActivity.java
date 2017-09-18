package br.ufpe.cin.if710.managers.location;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class FusedLocationActivity extends ListActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, Runnable {
    //E se o GPS estiver desabilitado, ou indisponível?
    //FusedLocationProvider tenta atacar estas situações
    //Mistura dados do GPS com triangulação de torres celulares e hotspots WiFi
    //Consome dados dos sensores, pode identificar que não há movimento

    //parte do GooglePlay Services, ou seja, proprietário e closed source
    //apenas disponível em dispositivos que tem a play store
    //dispositivos mais antigos não funcionam
    private GoogleApiClient playServices;

    ArrayList<Location> data = new ArrayList<Location>();
    ArrayAdapter<Location> adapter;

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
        //metodo que tenta obter localizacao
        run();
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void run() {
        Location loc=LocationServices.FusedLocationApi.getLastLocation(playServices);
        //se nao tem objeto valido, agenda pra tentar daqui a pouco
        /*
        if (loc == null) {
            getListView().postDelayed(this, 1000);
        }
        else {
            adapter.add(loc);
        }
        /**/

        //implementacao alternativa
        if (loc != null) {
            adapter.add(loc);
        }
        //getListView().postDelayed(this, 5000);
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

        //simplesmente centraliza o mapa na latitude/longitude escolhida
        //nao amarra com google maps
        String locData = "geo:"+loc.getLatitude()+","+loc.getLongitude();

        //abre streetview
        locData = "google.streetview:cbll="+loc.getLatitude()+","+loc.getLongitude();

        //abre navigation
        //locData = "google.navigation:q="+loc.getLatitude()+","+loc.getLongitude();

        Uri locationURI = Uri.parse(locData);

        Intent i = new Intent(Intent.ACTION_VIEW, locationURI);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

}