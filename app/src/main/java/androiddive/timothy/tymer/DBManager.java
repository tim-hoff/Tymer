package androiddive.timothy.tymer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Timothy on 11/10/2014.
 */
public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name,String totallen, String len1,String rep1,String len2,String rep2,String len3,
                       String rep3,String sound) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.TYMER_NAME, name);
        contentValue.put(DatabaseHelper.TOTAL_LEN, totallen);
        contentValue.put(DatabaseHelper.TYMER_LEN1, len1);
        contentValue.put(DatabaseHelper.TYMER_REP1, rep1);
        contentValue.put(DatabaseHelper.TYMER_LEN2, len2);
        contentValue.put(DatabaseHelper.TYMER_REP2, rep2);
        contentValue.put(DatabaseHelper.TYMER_LEN3, len3);
        contentValue.put(DatabaseHelper.TYMER_REP3, rep3);
        contentValue.put(DatabaseHelper.TYMER_SOUND, sound);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.TYMER_NAME,
                DatabaseHelper.TOTAL_LEN,
                DatabaseHelper.TYMER_LEN1,DatabaseHelper.TYMER_REP1, DatabaseHelper.TYMER_LEN2,
                DatabaseHelper.TYMER_REP2,DatabaseHelper.TYMER_LEN3,DatabaseHelper.TYMER_REP3,
                DatabaseHelper.TYMER_SOUND};

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String totallen,String len1,String rep1,String len2,String rep2,String len3,
                      String rep3,String sound){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TYMER_NAME, name);
        contentValues.put(DatabaseHelper.TOTAL_LEN, totallen);
        contentValues.put(DatabaseHelper.TYMER_LEN1, len1);
        contentValues.put(DatabaseHelper.TYMER_REP1, rep1);
        contentValues.put(DatabaseHelper.TYMER_LEN2, len2);
        contentValues.put(DatabaseHelper.TYMER_REP2, rep2);
        contentValues.put(DatabaseHelper.TYMER_LEN3, len3);
        contentValues.put(DatabaseHelper.TYMER_REP3, rep3);
        contentValues.put(DatabaseHelper.TYMER_SOUND,sound);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}

