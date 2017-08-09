package br.ufpe.cin.if710.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.botaoHello);
        TextView tv = (TextView) findViewById(R.id.textoHello);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicou("Clicou no text view...");
            }
        });

        //da no mesmo que...
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicou("Clicou no botao...");
            }
        });

        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);
        TextView outro = new TextView(this);
        outro.setText("Adicionado programaticamente");
        ll.addView(outro);

        //... isso aqui
        //b.setOnClickListener(this);
    }

    public void clicou(String s) {
        TextView tv = (TextView) findViewById(R.id.textoAula);
        tv.setText(s);

    }

    @Override
    public void onClick(View v) {
        clicou("aqui no botao tb...");
    }
}
