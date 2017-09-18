package br.ufpe.cin.if710.managers.location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufpe.cin.if710.managers.R;

public class LocationMapsActivity extends AppCompatActivity {

    //Existem diversos dispositivos Android
    //Diferentes maneiras de obter localização
    //Objetos LocationProvider são usados para obter localização
    // existem zero ou mais instâncias de LocationProvider,
    // uma para cada serviço de localização disponível no device
    // GPS_PROVIDER, NETWORK_PROVIDER
    //Usamos o LocationManager para obter informações.
    // eh papel do Manager escolher o LocationProvider adequado

    //Permissao eh necessaria COARSE ou FINE
    // GPS_PROVIDER so funciona com FINE_LOCATION
    // permissoes sao dangerous, então precisamos pedir em novas APIs


    //1. pegar o LocationManager
    //2. escolher o LocationProvider
    //2.1 oferecer escolha - pega lista de providers getProviders()
    //2.2 escolher automaticamente (de acordo com objeto Criteria)
    //setAltitude, setAccuracy...
    //getBestProvider()

    //3. getLastKnownLocation()
    // retorna objeto Location

    //4. registrar para receber updates
    //Alguns location providers não respondem imediatamente.
    // O GPS exige ativação do rádio e comunicação com satélites.

    private static final int ID_PERMISSION_REQUEST = 2505;
    private static final String KEY_IN_PERMISSION = "temPermissao";
    private boolean isInPermission=false;
    private Bundle state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.state=savedInstanceState;

        if (state!=null) {
            isInPermission=state.getBoolean(KEY_IN_PERMISSION, false);
        }

        if (hasAllPermissions(getDesiredPermissions())) {
            onReady();
        }
        else if (!isInPermission) {
            isInPermission=true;
            ActivityCompat.requestPermissions(this, netPermissions(getDesiredPermissions()), ID_PERMISSION_REQUEST);
        }
    }

    protected void onReady() {
        setContentView(R.layout.activity_location_maps);

        Button b1 = (Button) findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProvidersActivity.class));
            }
        });

        Button b2 = (Button) findViewById(R.id.btn2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LocationActivity.class));
            }
        });

        Button b3 = (Button) findViewById(R.id.btn3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FusedLocationActivity.class));
            }
        });

        Button b4 = (Button) findViewById(R.id.btn4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MapActivity.class));
            }
        });

        Button b5 = (Button) findViewById(R.id.btn5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FusedLocationMapActivity.class));
            }
        });
    }

    protected String[] getDesiredPermissions() {
        return(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
    }

    protected void onPermissionDenied() {
        Toast.makeText(this, "Permissao negada!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        isInPermission=false;

        if (requestCode==ID_PERMISSION_REQUEST) {
            if (hasAllPermissions(getDesiredPermissions())) {
                onReady();
            }
            else {
                onPermissionDenied();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IN_PERMISSION, isInPermission);
    }

    private boolean hasAllPermissions(String[] perms) {
        for (String perm : perms) {
            if (!hasPermission(perm)) {
                return(false);
            }
        }

        return(true);
    }

    protected boolean hasPermission(String perm) {
        return(ContextCompat.checkSelfPermission(this, perm)== PackageManager.PERMISSION_GRANTED);
    }

    private String[] netPermissions(String[] wanted) {
        ArrayList<String> result=new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return(result.toArray(new String[result.size()]));
    }
}
