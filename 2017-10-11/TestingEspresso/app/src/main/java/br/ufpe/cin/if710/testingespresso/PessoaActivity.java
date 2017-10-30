package br.ufpe.cin.if710.testingespresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PessoaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        Intent i = getIntent();
        String nome = i.getStringExtra(MainActivity.KEY_NOME);
        String login = i.getStringExtra(MainActivity.KEY_LOGIN);

        TextView pNome = findViewById(R.id.pessoa_Nome);
        TextView pLogin = findViewById(R.id.pessoa_Login);

        pNome.setText(nome);
        pLogin.setText(login);
    }
}
