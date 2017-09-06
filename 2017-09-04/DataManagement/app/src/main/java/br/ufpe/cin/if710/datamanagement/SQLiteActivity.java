package br.ufpe.cin.if710.datamanagement;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SQLiteActivity extends Activity {

    ListView listaEstados;
    Button adicionarEstado;
    SQLEstadosHelper db;
    //Cursor c = null;
    AsyncTask t = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        db = SQLEstadosHelper.getInstance(this);


        adicionarEstado = (Button) findViewById(R.id.btnAdicionaEstado);
        adicionarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdicionarEstadoActivity.class));
            }
        });

        listaEstados = (ListView) findViewById(R.id.listaEstados);


        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.estado,
                        null,
                        new String[] { SQLEstadosHelper.STATE_NAME, SQLEstadosHelper.STATE_CODE },
                        new int[] { R.id.estadoNome, R.id.estadoUF },
                        0
                );
        listaEstados.setAdapter(adapter);

        listaEstados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
                cursor.moveToPosition(position);

                String UF = cursor.getString(cursor.getColumnIndex(SQLEstadosHelper.STATE_CODE));

                //t = new RemoveTask().execute(UF);

                Intent i = new Intent(getApplicationContext(),EditarEstadoActivity.class);
                i.putExtra(SQLEstadosHelper.STATE_CODE, UF);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        t = new LoadCursorTask().execute();
    }

    @Override
    protected void onDestroy() {
        if (t!=null) {
            t.cancel(true);
        }
        ((SimpleCursorAdapter) listaEstados.getAdapter()).getCursor().close();
        db.close();
        super.onDestroy();
    }

    private abstract class BaseTask<T> extends AsyncTask<T, Void, Cursor> {
        Cursor doQuery() {
            Cursor result=
                    db.getReadableDatabase()
                            .query(SQLEstadosHelper.DATABASE_TABLE,
                                    SQLEstadosHelper.columns,
                                    null,
                                    null,
                                    null,
                                    null,
                                    SQLEstadosHelper.STATE_CODE);

            //força a query a ser executada
            //so executa quando fazemos algo que precisa do resultset
            result.getCount();

            return result;
        }

        @Override
        public void onPostExecute(Cursor result) {
            ((CursorAdapter)listaEstados.getAdapter()).changeCursor(result);
            //c = result;
            t = null;
        }
    }

    private class LoadCursorTask extends BaseTask<Void> {
        @Override
        protected Cursor doInBackground(Void... params) {
            return(doQuery());
        }
    }

    private class RemoveTask extends BaseTask<String> {
        @Override
        protected Cursor doInBackground(String... params) {
            String where = SQLEstadosHelper.STATE_CODE + " LIKE ?";
            String[] whereArgs = new String[] { params[0] };
            db.getWritableDatabase().delete(SQLEstadosHelper.DATABASE_TABLE, where, whereArgs);
            return(doQuery());
        }
    }



}
