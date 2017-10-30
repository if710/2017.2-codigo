package br.ufpe.cin.if710.leakcanarysample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {
    private static Button naoFacaIsto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        naoFacaIsto = (Button) findViewById(R.id.btnLeak);
    }

}
