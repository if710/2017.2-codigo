package br.ufpe.cin.if710.managers.power;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.ufpe.cin.if710.managers.R;

public class KeepScreenOn2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_screen_on2);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setKeepScreenOn(true);
    }
}
