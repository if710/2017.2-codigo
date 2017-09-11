package br.ufpe.cin.if710.contentconsumer;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class LerContatosArrayActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentResolver contentResolver = getContentResolver();
        String columns[] = new String[] { ContactsContract.Contacts.DISPLAY_NAME };
        //se nao tem permissao vai dar runtime exception
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, columns, null, null, null);
        Log.v("IF710", "QTDE: "+cursor.getCount());

        List<String> contacts = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                contacts.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            } while (cursor.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);

        setListAdapter(adapter);
    }
}
