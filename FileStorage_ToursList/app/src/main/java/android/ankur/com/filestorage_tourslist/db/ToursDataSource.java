package android.ankur.com.filestorage_tourslist.db;

import android.ankur.com.filestorage_tourslist.data.Tour;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur on 3/29/2015.
 */
public class ToursDataSource {

    private static final String LOGTAG = "TOURS";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;

    private static final String[] allColumns = {
            ToursDBOpenHelper.COLUMN_ID,
            ToursDBOpenHelper.COLUMN_TITLE,
            ToursDBOpenHelper.COLUMN_DESC,
            ToursDBOpenHelper.COLUMN_PRICE,
            ToursDBOpenHelper.COLUMN_IMAGE
    };

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

    public Tour create(Tour tour){
        ContentValues values = new ContentValues();
        values.put(ToursDBOpenHelper.COLUMN_TITLE, tour.getTitle());
        values.put(ToursDBOpenHelper.COLUMN_PRICE, tour.getPrice());
        values.put(ToursDBOpenHelper.COLUMN_IMAGE, tour.getImage());
        values.put(ToursDBOpenHelper.COLUMN_DESC, tour.getDescription());

        //Get autoincremented ID
        long insertID = db.insert(ToursDBOpenHelper.TABLE_TOURS, null, values);
        //add to tour object and return
        tour.setId(insertID);
        return tour;
    }

    public List<Tour> readAll(){
        List<Tour> tours = new ArrayList<Tour>();

        Cursor cursor = db.query(ToursDBOpenHelper.TABLE_TOURS, allColumns, null, null, null, null, null);

        Log.i(LOGTAG, "Read " + cursor.getCount() + " rows from table");
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Tour tour = new Tour();
                tour.setId(cursor.getLong(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_ID)));
                tour.setTitle(cursor.getString(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_TITLE)));
                tour.setDescription(cursor.getString(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_DESC)));
                tour.setPrice(cursor.getDouble(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_PRICE)));
                tour.setImage(cursor.getString(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_IMAGE)));
                tours.add(tour);
            }
        }
        return tours;
    }

    public void delete(Tour tour){
        long deleteID = tour.getId();
        //db.delete(ToursDBOpenHelper.TABLE_TOURS, ToursDBOpenHelper.COLUMN_ID ==
    }

}
