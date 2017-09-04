package br.ufpe.cin.if710.datamanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.btnMemoriaExterna);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MemoriaExternaActivity.class));
            }
        });

        Button b2 = (Button) findViewById(R.id.btnMemoriaInterna);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MemoriaInternaActivity.class));
            }
        });

        Button b3 = (Button) findViewById(R.id.btnSharedPreferences);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SharedPrefsActivity.class));
            }
        });

        Button b4 = (Button) findViewById(R.id.btnPreferencesActivity);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PrefsActivity.class));

            }
        });

        Button b5 = (Button) findViewById(R.id.btnSQLite);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SQLiteActivity.class));
            }
        });    }
}
