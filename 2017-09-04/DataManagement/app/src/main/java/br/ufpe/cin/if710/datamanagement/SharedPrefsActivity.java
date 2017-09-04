package br.ufpe.cin.if710.datamanagement;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class SharedPrefsActivity extends Activity {
    private static String RECORDE = "recorde";
    private int maiorPontuacao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);
        //High Score
        final TextView recorde = (TextView) findViewById(R.id.high_score_text);
        //Pontuação do Jogo
        final TextView pontuacaoAtual = (TextView) findViewById(R.id.game_score_text);
        //Botão de Jogar
        final Button jogar = (Button) findViewById(R.id.play_button);
        //Botão de Resetar
        final Button resetar = (Button) findViewById(R.id.reset_button);

        final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        maiorPontuacao = prefs.getInt(RECORDE,0);
        recorde.setText(String.valueOf(maiorPontuacao));


        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int val = r.nextInt(1000);
                pontuacaoAtual.setText(String.valueOf(val));
                if (val>maiorPontuacao) {
                    maiorPontuacao = val;
                    recorde.setText(String.valueOf(val));
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt(RECORDE,maiorPontuacao);
                    editor.commit();
                }
            }
        });

        resetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pontuacaoAtual.setText(String.valueOf(0));
                recorde.setText(String.valueOf(0));
                maiorPontuacao = 0;
                SharedPreferences.Editor editor = prefs.edit();
                //editor.putInt(RECORDE,maiorPontuacao);
                editor.remove(RECORDE);
                editor.commit();
            }
        });
    }
}
