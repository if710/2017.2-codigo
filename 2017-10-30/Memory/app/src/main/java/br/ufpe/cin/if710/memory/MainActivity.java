package br.ufpe.cin.if710.memory;

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

        final Button b1 = (Button) findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View src) {
                startActivity(new Intent(getApplicationContext(), StaticWidgetActivity.class));
            }
        });

        final Button b2 = (Button) findViewById(R.id.btn2);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View src) {
                startActivity(new Intent(getApplicationContext(), ThreadReferenceActivity.class));
            }
        });

        final Button b3 = (Button) findViewById(R.id.btn3);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View src) {
                //startActivity(new Intent(getApplicationContext(), ConfigChangeActivity.class));
            }
        });

        final Button b4 = (Button) findViewById(R.id.btn4);
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View src) {
                startActivity(new Intent(getApplicationContext(), MemoryInfoActivity.class));
            }
        });
    }

}
