package br.ufpe.cin.if710.datamanagement;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrefsActivity extends Activity {
    TextView textoUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);
        final Button button = (Button) findViewById(R.id.check_pref_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        PrefsActivity.this,
                        PrefsMenuActivity.class));
            }
        });
        textoUsername = (TextView) findViewById(R.id.textoUsername);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString(PrefsMenuActivity.USERNAME,"nada escolhido...");
        textoUsername.setText(username);
    }


}
