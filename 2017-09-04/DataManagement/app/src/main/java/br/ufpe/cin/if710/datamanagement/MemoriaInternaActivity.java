package br.ufpe.cin.if710.datamanagement;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MemoriaInternaActivity extends Activity {

    private final static String arquivo = "teste.txt";
    LinearLayout ll_conteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText texto = (EditText) findViewById(R.id.campoTexto);
        ll_conteudo = (LinearLayout) findViewById(R.id.ll_conteudoArquivo);
        Button salvar = (Button) findViewById(R.id.botaoSalvar);
        Button ler = (Button) findViewById(R.id.botaoLer);
        Button limpar = (Button) findViewById(R.id.botaoLimpar);
        Button apagar = (Button) findViewById(R.id.botaoApagar);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = texto.getText().toString();
                try {
                    escreverArquivo(txt);
                    texto.setText("");
                } catch (FileNotFoundException e) {
                    Log.i("MemoriaInterna", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparConteudo();
            }
        });

        ler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFileStreamPath(arquivo).exists()) {
                    try {
                        limparConteudo();
                        lerArquivo();
                    } catch (IOException e) {
                        Log.i("MemoriaInterna", e.getMessage());
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Arquivo inexistente",Toast.LENGTH_SHORT).show();
                }
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apagarArquivo();
                limparConteudo();
            }
        });


    }

    private void escreverArquivo(String txt) throws FileNotFoundException {
        //MODE_PRIVATE
        //MODE_APPEND
        FileOutputStream fos = openFileOutput(arquivo, MODE_APPEND);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
        pw.println(txt);
        pw.close();
    }

    private void lerArquivo() throws IOException {
        FileInputStream fis = openFileInput(arquivo);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String linha = "";

        while (null != (linha = br.readLine())) {
            TextView tv = new TextView(this);
            tv.setTextSize(16);
            tv.setText(linha);
            ll_conteudo.addView(tv);
        }
        br.close();
    }

    private void apagarArquivo() {
        if (getFileStreamPath(arquivo).exists()) {
            deleteFile(arquivo);
        }
        else {
            Toast.makeText(getApplicationContext(),"Arquivo inexistente", Toast.LENGTH_SHORT).show();
        }
    }

    void limparConteudo() {
        ll_conteudo.removeAllViews();
    }
}
