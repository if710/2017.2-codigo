package br.ufpe.cin.if710.leakcanarysample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class CanaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
    }
}
