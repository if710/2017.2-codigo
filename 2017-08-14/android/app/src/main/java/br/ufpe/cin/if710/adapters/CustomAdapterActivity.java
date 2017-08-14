package br.ufpe.cin.if710.adapters;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapterActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new PessoaAdapter());

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Pessoa p = (Pessoa) getListAdapter().getItem(position);
        Toast.makeText(this,p.getNome() + " - " + p.getLogin(),Toast.LENGTH_LONG).show();
    }

    class PessoaAdapter extends ArrayAdapter<Pessoa> {

        PessoaAdapter() {
            super(CustomAdapterActivity.this, R.layout.customadapter, R.id.nome, Constants.pessoas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View linha = super.getView(position, convertView, parent);
            TextView tvLogin = (TextView) linha.findViewById(R.id.login);
            String login = Constants.pessoas[position].getLogin();
            tvLogin.setText("(" + login +")");
            /**/
            return linha;
        }
    }
}
