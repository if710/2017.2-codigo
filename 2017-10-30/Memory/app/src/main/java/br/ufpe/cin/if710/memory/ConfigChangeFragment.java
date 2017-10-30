package br.ufpe.cin.if710.memory;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ConfigChangeFragment extends Fragment implements View.OnClickListener {
        private static final String[] items= { "lorem", "ipsum", "dolor",
                "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
                "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
                "vel", "erat", "placerat", "ante", "porttitor", "sodales",
                "pellentesque", "augue", "purus" };
        private ArrayList<String> model=new ArrayList<String>();
        private ArrayAdapter<String> adapter=null;
        private AddStringTask task=null;
        private Button btnAgain=null;
        private ListView listView=null;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setRetainInstance(true);

            task=new AddStringTask();
            task.execute();

            adapter=
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1,
                            model);
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {
            return(inflater.inflate(R.layout.activity_config_change, container, false));
        }

        @Override
        public void onViewCreated(View v, Bundle savedInstanceState) {
            super.onViewCreated(v, savedInstanceState);
            listView = getActivity().findViewById(R.id.lv);
            listView.setScrollbarFadingEnabled(false);
            listView.setAdapter(adapter);

            getAgain().setOnClickListener(this);

            if (task!=null) {
                getAgain().setEnabled(false);
            }
        }

        @Override
        public void onDestroy() {
            if (task!=null) {
                task.cancel(false);
            }

            super.onDestroy();
        }

    private Button getAgain() {
        if (btnAgain==null) {
            View v = getView();
            if (v!=null) {
                btnAgain = (Button) v.findViewById(R.id.btnLeak);
            }
            else {
                btnAgain = (Button) getActivity().findViewById(R.id.btnLeak);
            }
        }

        return(btnAgain);
    }

    @Override
    public void onClick(View v) {
        getAgain().setEnabled(false);
        adapter.clear();
        task=new AddStringTask();
        task.execute();
    }

    class AddStringTask extends AsyncTask<Void, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... unused) {
            for (String item : items) {
                if (isCancelled())
                    break;

                publishProgress(item);
                SystemClock.sleep(400);
            }

            return(null);
        }

        @Override
        protected void onProgressUpdate(String... item) {
            if (!isCancelled()) {
                adapter.add(item[0]);
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            task=null;
            getAgain().setEnabled(true);
        }
    }


}
