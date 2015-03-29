package android.ankur.com.filestorage_tourslist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ankur on 3/29/2015.
 */
public class ToursDataSource {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;

    public ToursDataSource(Context context) {
        dbhelper = new ToursDBOpenHelper(context);
        db = dbhelper.getWritableDatabase();
    }
}
