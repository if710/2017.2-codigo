package br.ufpe.cin.if710.contentconsumer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class ContentConsumerSQLiteActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentResolver cr = getContentResolver();
        //consulta na main thread, pode ser custoso, usar AsyncTask ou Loader
        Cursor c = cr.query(ContentProviderContract.CONTENT_ESTADOS_URI, null, null, null, null);
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(
                        this,
                        R.layout.itemlista,
                        c,
                        new String[] {ContentProviderContract.STATE_NAME, ContentProviderContract.STATE_CODE},
                        new int[] {R.id.pNome, R.id.pEmail},
                        0);
        setListAdapter(adapter);
    }
}
