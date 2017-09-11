package br.ufpe.cin.if710.contentconsumer;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class LerContatosCursorActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //nao tem setContentView
        ContentResolver contentResolver = getContentResolver();
        Uri contactsURI = ContactsContract.Contacts.CONTENT_URI;
        String colunas[] = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };

        String where = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND " +
                "(" + ContactsContract.Contacts.DISPLAY_NAME + " != '' ) AND " +
                "(" + ContactsContract.Contacts.STARRED + "== 1))";;
        String[] whereArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " ASC";

        //se nao tem permissao vai dar runtime exception
        Cursor c = contentResolver.query(
              contactsURI, colunas,
                where,
                whereArgs,
                sortOrder
        );

        String from[] = new String[] {ContactsContract.Contacts.DISPLAY_NAME};
        int to[] = new int[] { R.id.contactName };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            this, R.layout.contact, c, from, to, 0);

        setListAdapter(adapter);
    }
}
