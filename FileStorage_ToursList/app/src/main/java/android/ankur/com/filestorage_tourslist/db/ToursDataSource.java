package android.ankur.com.filestorage_tourslist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ankur on 3/29/2015.
 */
public class ToursDataSource {

    private static final String LOGTAG = "TOURS";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;

    public ToursDataSource(Context context) {
        dbhelper = new ToursDBOpenHelper(context);
    }

    public void openDB() {
        Log.i(LOGTAG, "Database Opened");
        db = dbhelper.getWritableDatabase();
    }

    public void closeDB() {
        Log.i(LOGTAG, "Database Closed");
        dbhelper.close();
    }

}
