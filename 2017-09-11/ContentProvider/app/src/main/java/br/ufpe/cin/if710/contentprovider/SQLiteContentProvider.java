package br.ufpe.cin.if710.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class SQLiteContentProvider extends ContentProvider {

    SQLEstadosHelper db;

    @Override
    public boolean onCreate() {
        db = SQLEstadosHelper.getInstance(getContext());
        return true;
    }

    // Final da URI é a tabela de estados?
    private boolean isEstadosUri(Uri uri) {
        return uri.getLastPathSegment().equals(ContentProviderContract.ESTADO_TABLE);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //VISAO GERAL
        //montar um cursor
        //popular com o que quer que eu esteja procurando
        //retornar

        Cursor cursor = null;

        if (isEstadosUri(uri)) {
            cursor = db.getReadableDatabase().query(SQLEstadosHelper.DATABASE_TABLE,projection, selection, selectionArgs,null,null,sortOrder);
        }
        else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        return cursor;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        if (isEstadosUri(uri)) {
            return db.getWritableDatabase().delete(SQLEstadosHelper.DATABASE_TABLE,selection,selectionArgs);
        }
        else return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (isEstadosUri(uri)) {
            long id = db.getWritableDatabase().insert(SQLEstadosHelper.DATABASE_TABLE,null,values);
            return Uri.withAppendedPath(ContentProviderContract.CONTENT_ESTADOS_URI, Long.toString(id));
        }
        else return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (isEstadosUri(uri)) {
            return db.getWritableDatabase().update(SQLEstadosHelper.DATABASE_TABLE, values, selection, selectionArgs);
        }
        else return 0;
    }
}
