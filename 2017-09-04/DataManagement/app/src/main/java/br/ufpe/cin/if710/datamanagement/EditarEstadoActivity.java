package br.ufpe.cin.if710.datamanagement;

import android.app.Activity;
import android.os.Bundle;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarEstadoActivity extends Activity {
    private SQLEstadosHelper db = null;
    EditText UF, nomeEstado;
    Button editar;
    String codigoEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alterar_estado);

        Intent i = getIntent();
        codigoEstado = i.getStringExtra(SQLEstadosHelper.STATE_CODE);

        db = SQLEstadosHelper.getInstance(this);

        UF = (EditText) findViewById(R.id.txtUF);
        nomeEstado = (EditText) findViewById(R.id.txtNome);
        editar = (Button) findViewById(R.id.btnInserirEstado);
        new CarregaTask().execute();
    }


    private class CarregaTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... params) {
            String selection = SQLEstadosHelper.STATE_CODE + " = ?";
            String[] selectionArgs = { codigoEstado };

            Cursor result =
                    db.getReadableDatabase()
                            .query(SQLEstadosHelper.DATABASE_TABLE,
                                    SQLEstadosHelper.columns,
                                    selection,
                                    selectionArgs,
                                    null,
                                    null,
                                    null);

            result.getCount();

            return result;
        }

        @Override
        public void onPostExecute(Cursor result) {
            if (result.moveToFirst()) {
                UF.setText(result.getString(result.getColumnIndex(SQLEstadosHelper.STATE_CODE)));
                nomeEstado.setText(result.getString(result.getColumnIndex(SQLEstadosHelper.STATE_NAME)));
                editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new EditaTask().execute(UF.getText().toString(), nomeEstado.getText().toString());
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(),"Erro na consulta",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private class EditaTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String selection = SQLEstadosHelper.STATE_CODE + " = ?";
            String[] selectionArgs = { params[0] };

            ContentValues cv = new ContentValues();
            cv.put(SQLEstadosHelper.STATE_CODE,params[0]);
            cv.put(SQLEstadosHelper.STATE_NAME,params[1]);

            db.getWritableDatabase().update(SQLEstadosHelper.DATABASE_TABLE,cv,selection,selectionArgs);
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            finish();
        }
    }
}
