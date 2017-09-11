package br.ufpe.cin.if710.contentconsumer;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class LerContatosLoaderActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String from[] = new String[]{ ContactsContract.Contacts.DISPLAY_NAME };
        int to[] = new int[]{ R.id.contactName };
        adapter = new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.contact,
                null,
                from,
                to,
                0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String columnsToExtract[] = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
        String whereClause = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND " +
                "(" + ContactsContract.Contacts.DISPLAY_NAME + " != '' ) AND " +
                "(" + ContactsContract.Contacts.STARRED + "== 1))";
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " ASC";
        Log.v("IF710", "onCreateLoader(...) ");

        //se nao tem permissao vai dar runtime exception
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, columnsToExtract, whereClause, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        Log.v("IF710", "onLoadFinished(...) "+data.getCount());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
        Log.v("IF710", "onLoaderReset() ");
    }
}
