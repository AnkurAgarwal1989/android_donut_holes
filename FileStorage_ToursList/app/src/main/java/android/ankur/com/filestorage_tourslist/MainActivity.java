package android.ankur.com.filestorage_tourslist;

import android.ankur.com.filestorage_tourslist.data.Tour;
import android.ankur.com.filestorage_tourslist.data.ToursJSONFileStorage;
import android.ankur.com.filestorage_tourslist.db.ToursDataSource;
import android.ankur.com.filestorage_tourslist.db.ToursMyDataSource;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    public static final String LOGTAG = "TOURS";
    public static final String VIEWIMAGEPREF = "viewImage";

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener prefsListener;

    ToursDataSource dataSource;
    ToursMyDataSource myDataSource;
    private List<Tour> tours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefsListener = new SharedPreferences.OnSharedPreferenceChangeListener(){
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.i(LOGTAG, "Preference Changed");
                MainActivity.this.refreshDisplay();
            }
        };
        //register listener
        prefs.registerOnSharedPreferenceChangeListener(prefsListener);


        //useJSONFile();
        //useXMLFile();

        dataSource = new ToursDataSource(this);
        dataSource.openDB();

        myDataSource = new ToursMyDataSource(this);
        myDataSource.openDB();

        tours = dataSource.readAll();
        if (tours.size() == 0){
            importXMLtoSQLite();
            tours = dataSource.readAll();
        }

        refreshDisplay();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Tour tour = tours.get(position);

        //List View position gives us position in the tours list which is a private member
        Intent intent = new Intent(this, TourDetailActivity.class);
        //Since we are using parcel, arg 1 is the class it is instance of, arg2 is the object...
        // the packaging happens internally in Tour class;
        intent.putExtra(".data.Tour", tour);
        startActivity(intent);
    }

    private void refreshDisplay() {

        Log.i(LOGTAG, "Display Updated");
        //If rich text is checked use ToursListAdapter
        if (getPrefs(null))
        {
            Log.i(LOGTAG, "Rich Display Updated");

            final ToursListAdapter toursListAdapter = new ToursListAdapter(this, R.layout.list_item_layout, tours);
            setListAdapter(toursListAdapter);
        }
        //Else use regular adapter
        else
        {
            Log.i(LOGTAG, "Basic Display Updated");

            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tours);
            setListAdapter(adapter);
        }

    }

    private void useXMLFile() {
        ToursPullParser toursPullParser = new ToursPullParser();
        tours = toursPullParser.parseXML(this);

        refreshDisplay();
        //ArrayAdapter<Tour> tourArray = new ArrayAdapter<Tour>(this, R.layout.list_item_layout,R.id.listItem_Label, tours);
        //setListAdapter(tourArray);
    }

    private void useJSONFile() {
        //For JSON file stored in external memory
        ToursJSONFileStorage fileStorageHelper = null;
        try {
            fileStorageHelper = new ToursJSONFileStorage(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray tours = null;
        if (fileStorageHelper != null) {
            try {
                tours = fileStorageHelper.readJSONFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final ArrayList<String> toursList = new ArrayList<String>();
        for (int i = 0; i < tours.length(); i++) {
            try {
                String tourName = tours.getJSONObject(i).getString("tour");
                toursList.add(tourName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_layout, R.id.listItem_Label, toursList);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.action_my:
                tours = myDataSource.findMyTours();
                break;

            case R.id.action_all:
                tours = dataSource.readAll();
                break;

            case R.id.action_basic:
                tours = dataSource.readFiltered("price <= 300", "price ASC");
                break;

            case R.id.action_premium:
                tours = dataSource.readFiltered("price > 300", "price DESC");
                break;

            default:
                break;
        }
        refreshDisplay();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.openDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.closeDB();
    }

    private void importXMLtoSQLite(){

        ToursPullParser toursPullParser = new ToursPullParser();
        List<Tour> tours;
        tours = toursPullParser.parseXML(this);

        for (Tour tour : tours){
            tour = dataSource.create(tour);
            Log.i(LOGTAG, "New tour item added wit ID: " + tour.getId());
        }

    }

    public boolean getPrefs(View v){
        Log.i(LOGTAG, "Getting prefs.");
        return prefs.getBoolean(VIEWIMAGEPREF, false);
    }
}
