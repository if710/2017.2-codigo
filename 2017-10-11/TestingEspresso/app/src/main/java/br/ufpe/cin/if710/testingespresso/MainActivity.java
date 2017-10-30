package br.ufpe.cin.if710.testingespresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    public static final String KEY_NOME = "NOME";
    public static final String KEY_LOGIN = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.lista);

        //criando o adapter
        ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(
                this,
                android.R.layout.simple_list_item_1,
                Constants.maisPessoas
        );

        //setando o adapter na listview
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView lv = (ListView) adapterView;
                ArrayAdapter<Pessoa> a = (ArrayAdapter<Pessoa>) lv.getAdapter();
                Pessoa p = a.getItem(i);

                Intent intent = new Intent(getApplicationContext(),PessoaActivity.class);
                intent.putExtra(KEY_NOME, p.getNome());
                intent.putExtra(KEY_LOGIN, p.getLogin());
                startActivity(intent);

            }
        });
    }
}
