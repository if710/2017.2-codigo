package br.ufpe.cin.if710.managers.pkg;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import static android.content.pm.PackageManager.GET_ACTIVITIES;
import static android.content.pm.PackageManager.GET_META_DATA;
import static android.content.pm.PackageManager.MATCH_ALL;

public class PackageManagerQueryActivity extends ListActivity {
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String query = i.getStringExtra(PackageManagerActivity.QUERY_KEY);
        packageManager = getPackageManager();


        if (query.equals(PackageManagerActivity.GET_PKGS)) {
            List<PackageInfo> info = packageManager.getInstalledPackages(GET_ACTIVITIES);
            ArrayAdapter<PackageInfo> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, info);
            setListAdapter(adapter);
        }
        else if (query.equals(PackageManagerActivity.GET_APPS)) {
            List<ApplicationInfo> info = packageManager.getInstalledApplications(GET_META_DATA);
            ArrayAdapter<ApplicationInfo> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, info);
            setListAdapter(adapter);
        }
        else if (query.equals(PackageManagerActivity.GET_BROADCASTS)) {
            Intent intent = new Intent(Intent.ACTION_BOOT_COMPLETED);
            intent = new Intent(Intent.ACTION_BATTERY_LOW);
            List<ResolveInfo> info = packageManager.queryBroadcastReceivers(intent,MATCH_ALL);
            ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, info);
            setListAdapter(adapter);
        }
        else {
            Toast.makeText(this,"Ainda a implementar...",Toast.LENGTH_SHORT).show();
        }

    }
}
