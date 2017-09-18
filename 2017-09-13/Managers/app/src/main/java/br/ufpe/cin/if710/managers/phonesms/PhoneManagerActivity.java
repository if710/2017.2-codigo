package br.ufpe.cin.if710.managers.phonesms;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class PhoneManagerActivity extends ListActivity {

    ArrayList<String> data = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean readPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        boolean accessCoarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (readPhoneState && accessCoarseLocation) {
            adapter.add("Device ID: " + telephonyManager.getDeviceId());
            adapter.add("Call State: " + telephonyManager.getCallState());
            adapter.add("Device SW Version: " + telephonyManager.getDeviceSoftwareVersion());
            adapter.add("Network Operator: " + telephonyManager.getNetworkOperator());
            adapter.add("Network Operator Name: " + telephonyManager.getNetworkOperatorName());
            adapter.add("Network Country ISO: " + telephonyManager.getNetworkCountryIso());
            adapter.add("Network Type: " + telephonyManager.getNetworkType());
            adapter.add("Cell Location: " + telephonyManager.getCellLocation());
            adapter.add("SIM Operator: " + telephonyManager.getSimOperator());
            adapter.add("SIM Serial Number: " + telephonyManager.getSimSerialNumber());
            adapter.add("SIM State: " + telephonyManager.getSimState());
            adapter.add("Voice Mail Number: " + telephonyManager.getVoiceMailNumber());
            adapter.add("Phone Type: " + telephonyManager.getPhoneType());

            //adapter.add...
        }
        else {
            Toast.makeText(this,"Conceda permissões em settings", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.clear();
    }
}