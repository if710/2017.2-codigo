package br.ufpe.cin.if710.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class ListContentProvider extends ContentProvider {
    List<Pessoa> pessoas;

    @Override
    public boolean onCreate() {
        pessoas = new ArrayList<>();
        pessoas.add(new Pessoa(0,"Professor", "1234", "lasdasdmt@cin.ufpe.br", 8.50f));
        pessoas.add(new Pessoa(1,"Teste 1", "4567", "lmtasd@cin.ufpe.br", 6.50f));
        pessoas.add(new Pessoa(2,"Nome Longo", "5678", "lmasdasdt@cin.ufpe.br", 7.50f));
        pessoas.add(new Pessoa(3,"Teste 2", "6789", "lmasdt@cin.ufpe.br", 0.50f));
        return true;
    }

    // final da URI é composta de digitos?
    private boolean isPessoaEspecificaUri(Uri uri) {
        return uri.getLastPathSegment().matches("\\d+");
    }

    // Final da URI é a tabela de pessoas?
    private boolean isPessoasUri(Uri uri) {
        return uri.getLastPathSegment().equals(ContentProviderContract.PESSOA_TABLE);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // poderia iniciar com projection
        MatrixCursor cursor = new MatrixCursor(ContentProviderContract.ALL_COLUMNS);

        if (isPessoasUri(uri)) {
            //ignorando selection, sortorder, etc.
            for (Pessoa p : pessoas) {
                cursor.addRow(new Object[] {p.get_id(), p.getNome(), p.getCpf(), p.getEmail(), p.getMedia()});
            }
        }
        else if (isPessoaEspecificaUri(uri)) {
            Integer reqId = Integer.parseInt(uri.getLastPathSegment());
            if (pessoas.get(reqId) !=null ) {
                Pessoa p = pessoas.get(reqId);
                cursor.addRow(new Object[] {p.get_id(), p.getNome(), p.getCpf(), p.getEmail()});
            }
        }
        else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
