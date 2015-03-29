package android.ankur.com.filestorage_tourslist.db;

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
    private static final int DATABASE_VERSION = 1;

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

    public ToursDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURS);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG, "Table created in database");
    }


}
