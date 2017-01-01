package AppLogic.StorageLogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import AppLogic.RecordsLogic.GameRecord;

/**
 * Created by Guy on 26/12/2016.
 */

public class StorageController {
    private Storage storageHelper;
    private SQLiteDatabase dbForWrite;
    private SQLiteDatabase dbForRead;

    public StorageController(Context context){
        this.storageHelper  = new Storage(context);
        this.dbForWrite     = storageHelper.getWritableDatabase();
        this.dbForRead      = storageHelper.getReadableDatabase();
    }
    public void addNewRecord(String name, int timeMs, int level){
        ContentValues values = new ContentValues();
        values.put(StorageConfig.COLUMN_PERSON_NAME, name);
        values.put(StorageConfig.COLUMN_TIME_MS, timeMs);
        values.put(StorageConfig.COLUMN_LEVEL, level);
        long newRowId = dbForWrite.insert(StorageConfig.TABLE_NAME, null, values);
        Log.v("Guy","Insert new record");
    }
    public ArrayList<GameRecord> getRecords(int level){
        ArrayList<GameRecord> gameRecordsArr = new ArrayList<>();
        String[] projection = {
                StorageConfig.COLUMN_ID,
                StorageConfig.COLUMN_PERSON_NAME,
                StorageConfig.COLUMN_TIME_MS,
                StorageConfig.COLUMN_LEVEL
        };
        String selection = StorageConfig.COLUMN_LEVEL + " = ?";
        String[] selectionArgs = { ((Integer)level).toString() };

        String sortOrder =
                StorageConfig.COLUMN_TIME_MS + " ASC";
        Cursor cursor = dbForRead.query(
                StorageConfig.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        while(cursor.moveToNext()) {
            int recordId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(StorageConfig.COLUMN_ID));
            String personName = cursor.getString(
                    cursor.getColumnIndexOrThrow(StorageConfig.COLUMN_PERSON_NAME));
            int timeMs = cursor.getInt(
                    cursor.getColumnIndexOrThrow(StorageConfig.COLUMN_TIME_MS));
            GameRecord gameRecord = new GameRecord(recordId, timeMs, level, personName);
            gameRecordsArr.add(gameRecord);
        }
        cursor.close();
        return gameRecordsArr;
    }
}
