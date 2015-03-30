package android.ankur.com.filestorage_tourslist.db;

import android.ankur.com.filestorage_tourslist.MainActivity;
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
public class ToursMyDataSource {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;

    public ToursMyDataSource(Context context){
        dbHelper = new ToursDBOpenHelper(context);
    }

    public void openDB(){
        db = dbHelper.getWritableDatabase();
    }

    public void closeDB(){
        dbHelper.close();
    }

    public boolean addToMyTours(Tour tour){
        ContentValues values = new ContentValues();
        values.put(ToursDBOpenHelper.COLUMN_ID, tour.getId());

        long insertID = db.insert(ToursDBOpenHelper.TABLE_MYTOURS, null, values);

        return (insertID != -1);
    }

    public List<Tour> findMyTours(){
        String query = "SELECT " + ToursDBOpenHelper.TABLE_TOURS + ".* FROM " +
                ToursDBOpenHelper.TABLE_TOURS + " JOIN " + ToursDBOpenHelper.TABLE_MYTOURS + " ON " +
                ToursDBOpenHelper.TABLE_TOURS + "." + ToursDBOpenHelper.COLUMN_ID +
                " = " + ToursDBOpenHelper.TABLE_MYTOURS + "." + ToursDBOpenHelper.COLUMN_ID;

        Cursor cursor = db.rawQuery(query, null);
        Log.i(MainActivity.LOGTAG, "Read " + cursor.getCount() + " rows from table");

        List<Tour> tours = cursorToList(cursor);
        return tours;
    }

    private List<Tour> cursorToList(Cursor cursor) {
        List<Tour> tours = new ArrayList<Tour>();
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

    private boolean 




}
