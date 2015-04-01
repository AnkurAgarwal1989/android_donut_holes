package android.ankur.com.webservices_flowercatalog;

import android.ankur.com.webservices_flowercatalog.data.Flower;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    //TextView outputTextView;
    String testText;

    List<DownloadFlowerNamesTask> tasks;
    List<Flower> flowerList;

    ProgressBar pb;

    public static final String LOGTAG = "FLOWERS";

    public String URLSTRING = "https://services.hanselandpetal.com/secure/flowers.xml";
    //public String URLSTRING = "http://services.hanselandpetal.com/feeds/flowers.xml";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //outputTextView = (TextView) findViewById(R.id.textView);
        //outputTextView.setMovementMethod(new ScrollingMovementMethod());

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
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
        if (id == R.id.action_update) {
            if (isOnline())
            {
                requestData(URLSTRING);
            }
            else
            {
                Toast.makeText(this, "Network connection not found", Toast.LENGTH_LONG).show();
            }

        }
        return false;
    }

    private void requestData(String uri) {
        new DownloadFlowerNamesTask().execute(uri);
    }

    //Async class declared as inner class in activity
    private class DownloadFlowerNamesTask extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting task");
            if(tasks.size() == 0)
                pb.setVisibility(View.VISIBLE);
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                //params[0] holds the uri right now.
                return HttpManager.getData(params[0], "feeduser", "feedpassword");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {

            tasks.remove(this);
            if(tasks.size() == 0)
                pb.setVisibility(View.INVISIBLE);

            if (s == null)
                Toast.makeText(MainActivity.this, "Could not connect to web service", Toast.LENGTH_LONG).show();
            else
                updateDisplay(s);

        }
    }

    private void updateDisplay(String text) {
        Log.i(LOGTAG, text);
        flowerList = FlowersXMLPullParser.parseXML(text);
        FlowersListAdapter flowersListAdapter = new FlowersListAdapter(this,R.layout.list_item_flower, flowerList);
        setListAdapter(flowersListAdapter);

        //outputTextView.append(text + "\n" );
    }
}
