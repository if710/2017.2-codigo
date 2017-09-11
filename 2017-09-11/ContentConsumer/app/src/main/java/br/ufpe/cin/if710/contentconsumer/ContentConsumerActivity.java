package br.ufpe.cin.if710.contentconsumer;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContentConsumerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_consumer);
        ListView lv_pessoas = (ListView) findViewById(R.id.lv_Pessoas);
        ContentResolver cr = getContentResolver();
        //consulta na main thread, pode ser custoso, usar AsyncTask ou Loader
        Cursor c = cr.query(ContentProviderContract.CONTENT_LIST_URI, null, null, null, null);
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.itemlista,
                        c,
                        new String[] {ContentProviderContract.NOME},
                        new int[] {R.id.pNome},
                        0);
        lv_pessoas.setAdapter(adapter);
    }
}
