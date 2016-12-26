package AppLogic.StorageLogic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Guy on 26/12/2016.
 */

public class Storage extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "minesweeper.db";

    public Storage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StorageConfig.TABLE_NAME + "(" +
                    StorageConfig.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StorageConfig.COLUMN_PERSON_NAME + " TEXT," +
                    StorageConfig.COLUMN_TIME_MS + " INTEGER," +
                    StorageConfig.COLUMN_LEVEL + " INTEGER )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StorageConfig.TABLE_NAME;
}
