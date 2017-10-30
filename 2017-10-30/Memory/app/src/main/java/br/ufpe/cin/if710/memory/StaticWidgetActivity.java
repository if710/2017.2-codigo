package br.ufpe.cin.if710.memory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class StaticWidgetActivity extends Activity {
    private static Button naoFacaIsto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_widget);

        naoFacaIsto = (Button) findViewById(R.id.btnLeak);

    }
}
