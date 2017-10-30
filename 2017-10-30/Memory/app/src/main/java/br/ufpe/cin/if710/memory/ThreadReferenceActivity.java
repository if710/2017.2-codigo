package br.ufpe.cin.if710.memory;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

public class ThreadReferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_reference);

        new Thread() {
            public void run() {
                while (true) {
                    SystemClock.sleep(100);
                }
            }
        }.start();
    }
}
