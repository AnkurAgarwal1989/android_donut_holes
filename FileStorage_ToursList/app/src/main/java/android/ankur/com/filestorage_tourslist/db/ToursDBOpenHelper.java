package android.ankur.com.filestorage_tourslist.db;

import android.ankur.com.filestorage_tourslist.MainActivity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Ankur on 3/28/2015.
 */
public class ToursDBOpenHelper extends SQLiteOpenHelper {

    //Database requirements...we need to have a name for database, its tables and their respective columns.
    private static final String LOGTAG = "TOURS";

    //Database name
    private static final String DATABASE_NAME = "tours.db";
    private static final int DATABASE_VERSION = 2;

    //Table Name for this app
    public static final String TABLE_TOURS = "tours";
    public static final String COLUMN_ID = "tourID";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "image";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TOURS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESC + " TEXT, " +
                    COLUMN_PRICE + " NUMERIC, " +
                    COLUMN_IMAGE + " TEXT " + ")";

    public static final String TABLE_MYTOURS = "mytours";
    private static final String TABLE_MYTOURS_CREATE =
            "CREATE TABLE " + TABLE_MYTOURS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY " +")";


    public ToursDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_MYTOURS_CREATE);
        Log.i(LOGTAG, "Tables created in database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(MainActivity.LOGTAG, "Database upgraded");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYTOURS);
        onCreate(db);
    }
}
