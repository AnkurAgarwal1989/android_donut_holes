package android.ankur.com.filestorage_tourslist;

import android.ankur.com.filestorage_tourslist.data.ToursFileStorage;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ListActivity {

    private static final String LOGTAG = "TOURS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView listView = (ListView) findViewById(R.id.listView_Tours);
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("trip 1");
        arrayList.add("trip 2");

        ToursFileStorage fileStorageHelper = null;
        try {
            fileStorageHelper = new ToursFileStorage(this);
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

        //final ToursListAdapter toursListAdapter = new ToursListAdapter()
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
