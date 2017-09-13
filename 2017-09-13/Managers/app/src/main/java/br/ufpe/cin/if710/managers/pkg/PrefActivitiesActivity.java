package br.ufpe.cin.if710.managers.pkg;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class PrefActivitiesActivity extends ListActivity {
    PackageManager pm;
    //CustomAdapter adapter;
    ArrayList<IntentFilter> filters = new ArrayList<>();
    ArrayList<ComponentName> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pm = getPackageManager();

        pm.getPreferredActivities(filters, names, null);
        setListAdapter(new IntentFilterAdapter());

    }

    // from http://stackoverflow.com/a/8555153/115145

    public static <T> Iterable<T> in(final Iterator<T> iterator) {
        class SingleUseIterable implements Iterable<T> {
            private boolean used=false;

            @Override
            public Iterator<T> iterator() {
                if (used) {
                    throw new IllegalStateException("Already invoked");
                }
                used=true;
                return iterator;
            }
        }
        return new SingleUseIterable();
    }

    class IntentFilterAdapter extends ArrayAdapter<IntentFilter> {
        IntentFilterAdapter() {
            super(PrefActivitiesActivity.this,
                    android.R.layout.simple_list_item_2,
                    android.R.id.text1,
                    filters);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=super.getView(position, convertView, parent);
            TextView filter=(TextView)row.findViewById(android.R.id.text1);
            TextView name=(TextView)row.findViewById(android.R.id.text2);

            filter.setText(buildTitle(getItem(position)));
            name.setText(names.get(position).getClassName());

            return(row);
        }

        String buildTitle(IntentFilter filter) {
            StringBuilder buf=new StringBuilder();
            boolean first=true;

            if (filter.countActions() > 0) {
                for (String action : in(filter.actionsIterator())) {
                    if (first) {
                        first=false;
                    }
                    else {
                        buf.append('/');
                    }

                    buf.append(action.replaceAll("android.intent.action.", ""));
                }
            }

            if (filter.countDataTypes() > 0) {
                first=true;

                for (String type : in(filter.typesIterator())) {
                    if (first) {
                        buf.append(" : ");
                        first=false;
                    }
                    else {
                        buf.append('|');
                    }

                    buf.append(type);
                }
            }

            if (filter.countDataSchemes() > 0) {
                buf.append(" : ");
                buf.append(filter.getDataScheme(0));

                if (filter.countDataSchemes() > 1) {
                    buf.append(" (other schemes)");
                }
            }

            if (filter.countDataPaths() > 0) {
                buf.append(" : ");
                buf.append(filter.getDataPath(0));

                if (filter.countDataPaths() > 1) {
                    buf.append(" (other paths)");
                }
            }

            return(buf.toString());
        }
    }
}