package br.ufpe.cin.if710.contentprovider;

import android.content.ContentResolver;
import android.net.Uri;

public final class ContentProviderContract {
    public static final String _ID = "_id";
    public static final String NOME = "nome";
    public static final String CPF = "cpf";
    public static final String EMAIL = "email";
    public static final String MEDIA = "media";
    public static final String PESSOA_TABLE = "pessoa";


    public final static String STATE_CODE = "uf";
    public final static String STATE_NAME = "name";
    public final static String[] ESTADO_COLUMNS = { _ID, STATE_CODE, STATE_NAME };
    public static final String ESTADO_TABLE = "estado";



    private static final Uri BASE_LIST_URI = Uri.parse("content://br.ufpe.cin.if710.listprovider/");
    //URI para tabela
    public static final Uri CONTENT_LIST_URI = Uri.withAppendedPath(BASE_LIST_URI, PESSOA_TABLE);

    private static final Uri BASE_SQL_URI = Uri.parse("content://br.ufpe.cin.if710.sqlprovider/");
    //URI para tabela
    public static final Uri CONTENT_ESTADOS_URI = Uri.withAppendedPath(BASE_SQL_URI, ESTADO_TABLE);

    // Mime type para colecao de itens
    public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/PessoaContentProvider.data.text";

    // Mime type para um item especifico
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/PessoaContentProvider.data.text";

    // Todas as colunas do content provider
    public static final String[] ALL_COLUMNS = { _ID, NOME, CPF, EMAIL, MEDIA };
}
