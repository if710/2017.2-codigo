package br.ufpe.cin.if710.datamanagement;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdicionarEstadoActivity extends Activity {
    private SQLEstadosHelper db = null;
    EditText UF, nomeEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alterar_estado);
        db = SQLEstadosHelper.getInstance(this);

        UF = (EditText) findViewById(R.id.txtUF);
        nomeEstado = (EditText) findViewById(R.id.txtNome);

        Button b = (Button) findViewById(R.id.btnInserirEstado);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsereTask().execute(UF.getText().toString(), nomeEstado.getText().toString());
            }
        });
    }

    private class InsereTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            ContentValues cv = new ContentValues();
            cv.put(SQLEstadosHelper.STATE_CODE,params[0]);
            cv.put(SQLEstadosHelper.STATE_NAME,params[1]);
            db.getWritableDatabase().insert(SQLEstadosHelper.DATABASE_TABLE,null,cv);
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            finish();
        }
    }
}
