package android.ankur.com.filestorage_tourslist;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        final ArrayAdapter adapter = new ArrayAdapter(this,R.layout.list_item_layout, R.id.listItem_Label, arrayList);
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
