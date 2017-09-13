package br.ufpe.cin.if710.managers.pkg;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import br.ufpe.cin.if710.managers.R;


public class LauncherActivity extends ListActivity {
    PackageManager pm;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pegando o manager
        pm = getPackageManager();

        //criando intent que identifica activities 'launchable'
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        //pega todas as activities que casam com o intent fornecido - launchers
        List<ResolveInfo> launcherActivities = pm.queryIntentActivities(i, 0);

        //ordena
        Collections.sort(launcherActivities, new ResolveInfo.DisplayNameComparator(pm));

        //usa um custom adapter para definir o conteudo dos itens da lista
        adapter=new CustomAdapter(pm, launcherActivities);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //ResolveInfo rInfoAlt = (ResolveInfo) l.getAdapter().getItem(position);
        ResolveInfo resolveInfo = adapter.getItem(position);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        ComponentName cName = new ComponentName(activityInfo.applicationInfo.packageName,
                activityInfo.name);

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        //inicia novo task
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        i.setComponent(cName);
        startActivity(i);
    }

    class CustomAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm=null;

        CustomAdapter(PackageManager pm, List<ResolveInfo> apps) {
            super(LauncherActivity.this, R.layout.app, apps);
            this.pm=pm;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null) {
                convertView=newView(parent);
            }

            bindView(position, convertView);

            return(convertView);
        }

        private View newView(ViewGroup parent) {
            return(getLayoutInflater().inflate(R.layout.app, parent, false));
        }

        private void bindView(int position, View row) {
            TextView label=(TextView)row.findViewById(R.id.appName);
            label.setText(getItem(position).loadLabel(pm));
            ImageView icon=(ImageView)row.findViewById(R.id.icon);
            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }
}