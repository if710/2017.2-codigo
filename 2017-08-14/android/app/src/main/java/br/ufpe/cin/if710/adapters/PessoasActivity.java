package br.ufpe.cin.if710.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PessoasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas);

        ListView listView = (ListView) findViewById(R.id.listaPessoas);

        //criando o adapter
        ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(
                this,
                android.R.layout.simple_list_item_1,
                Constants.pessoas
        );

        //setando o adapter na listview
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pegando uma referencia para o elemento pai (ListView)
                ListView lv = (ListView) parent;
                //pegar uma referencia para o adapter definido ali em cima
                ArrayAdapter<Pessoa> a = (ArrayAdapter<Pessoa>) lv.getAdapter();
                //quero pegar o elemento, e ai?
                Pessoa p = a.getItem(position);

                Toast.makeText(getApplicationContext(),p.getLogin(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}
