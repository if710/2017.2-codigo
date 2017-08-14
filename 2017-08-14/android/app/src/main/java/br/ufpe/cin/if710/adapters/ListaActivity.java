package br.ufpe.cin.if710.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaActivity extends Activity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        //pegando referencias
        tv = (TextView) findViewById(R.id.tv1);
        ListView listView = (ListView) findViewById(R.id.lista1);

        //criando o adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                Constants.lista
                );

        //setando o adapter na listview
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pegando uma referencia para o elemento pai (ListView)
                ListView lv = (ListView) parent;
                //pegar uma referencia para o adapter definido ali em cima
                ArrayAdapter<String> a = (ArrayAdapter<String>) lv.getAdapter();
                //quero pegar o elemento, e ai?
                String s = a.getItem(position);

                tv.setText(s);


                //alternativa
                //String s2 = (String) ((ListView) parent).getAdapter().getItem(position);
                //Constants.lista[position]

            }
        });
    }
}
