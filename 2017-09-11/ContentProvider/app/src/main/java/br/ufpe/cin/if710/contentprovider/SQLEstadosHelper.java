package br.ufpe.cin.if710.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLEstadosHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "if710";
    public static final String DATABASE_TABLE = "estados";
    private static final int DB_VERSION = 1;

    String[] estadosBrasil;

    //alternativa
    //Context c;

    private SQLEstadosHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);

        estadosBrasil = context.getResources().getStringArray(R.array.estadosBrasil);

        //alternativa
        //c = context;
    }

    private static SQLEstadosHelper db;

    public static SQLEstadosHelper getInstance(Context c) {
        if (db==null) {
            db = new SQLEstadosHelper(c.getApplicationContext());
        }
        return db;
    }

    public final static String STATE_CODE = "uf";
    public final static String STATE_NAME = "name";
    public final static String _ID = "_id";
    public final static String[] columns = { _ID, STATE_CODE, STATE_NAME };
    final private static String CREATE_CMD =
            "CREATE TABLE "+DATABASE_TABLE+" (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + STATE_CODE + " VARCHAR(2) NOT NULL, "
                    + STATE_NAME + " TEXT NOT NULL)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);

        ContentValues cv = new ContentValues();
        cv.put(STATE_NAME, "indefinido");

        for (String estado : estadosBrasil) {
            //inserir estado
            cv.put(STATE_CODE, estado);
            db.insert(DATABASE_TABLE, STATE_NAME, cv);
        }

        //alternativa
        //String[] estados = c.getResources().getStringArray(R.array.estadosBrasil);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("nao se aplica");
    }
}
