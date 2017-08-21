package br.ufpe.cin.if710.rss;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private final String RSS_FEED = "http://rss.cnn.com/rss/edition.rss";
    private TextView conteudoRSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conteudoRSS = (TextView) findViewById(R.id.conteudoRSS);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new CarregarRSS().execute(RSS_FEED, "outrolink", "maisum");
    }

    private class CarregarRSS extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            conteudoRSS.setText("nada carregado ainda...");
        }

        @Override
        protected String doInBackground(String... params) {
            String feed = "carregando...";

            //publicando progresso (so pra exercitar e visualizar)
            publishProgress(feed);

            try {
                //poderia ser um for ... pegando todas as strings passadas
                //atualmente so pega o primeiro link
                feed = getRssFeed(params[0]);
            } catch (IOException e) {
                feed = "deu algum problema";
                e.printStackTrace();
            }
            return feed;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            conteudoRSS.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            conteudoRSS.setText(s);
        }
    }


    private String getRssFeed(String feed) throws IOException {
        InputStream in = null;
        String rssFeed = "";
        try {
            URL url = new URL(feed);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) != -1; ) {
                out.write(buffer,0,count);
            }
            byte[] response = out.toByteArray();
            rssFeed = new String(response,"UTF-8");
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return rssFeed;
    }
}
