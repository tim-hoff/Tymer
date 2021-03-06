package androiddive.timothy.tymer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Timothy on 11/10/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    // Table Name
    public static final String TABLE_NAME = "TIMERS";

    // Table columns
    public static final String _ID = "_id";
    public static final String TYMER_NAME = "name";
    public static final String TOTAL_LEN = "totallen";
    public static final String TYMER_LEN1 = "len1";
    public static final String TYMER_REP1 = "rep1";
    public static final String TYMER_LEN2 = "len2";
    public static final String TYMER_REP2 = "rep2";
    public static final String TYMER_LEN3 = "len3";
    public static final String TYMER_REP3 = "rep3";
    public static final String TYMER_SOUND ="sound";


    // Database Information
    static final String DB_NAME = "TIMERS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " +
            TABLE_NAME  + "(" +
            _ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TYMER_NAME  + " TEXT, "+
            TOTAL_LEN   + " TEXT, "+
            TYMER_LEN1  + " TEXT, "+
            TYMER_REP1  + " TEXT, "+
            TYMER_LEN2  + " TEXT, "+
            TYMER_REP2  + " TEXT, "+
            TYMER_LEN3  + " TEXT, "+
            TYMER_REP3  + " TEXT, "+
            TYMER_SOUND + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
