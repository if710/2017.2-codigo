package br.ufpe.cin.if710.managers.location;

import android.app.ListActivity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ProvidersActivity extends ListActivity {
    private LocationManager mgr;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = mgr.getAllProviders();

        //apenas habilitados
        //providers = mgr.getProviders(true);

        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, providers);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String provider = (String) l.getAdapter().getItem(position);
        String txt = "desabilitado";
        if (mgr.isProviderEnabled(provider)) {
            txt = "habilitado";
        }

        Toast.makeText(this, provider + " - " + txt, Toast.LENGTH_SHORT).show();
    }
}