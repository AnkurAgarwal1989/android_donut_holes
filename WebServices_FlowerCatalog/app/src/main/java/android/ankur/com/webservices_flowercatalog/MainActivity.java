package android.ankur.com.webservices_flowercatalog;

import android.ankur.com.webservices_flowercatalog.data.Flower;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    //TextView outputTextView;
    String testText;

    List<DownloadFlowerNamesTask> tasks;
    List<Flower> flowerList;

    ProgressBar pb;

    public static final String LOGTAG = "FLOWERS";

    public String URL_PRODUCT = "http://services.hanselandpetal.com/secure/flowers.xml";

    //Each flower name has an image @ this location...eg.
    //agapanthus is @ /photos/agapanthus.jpg
    public String URL_PHOTO = "http://services.hanselandpetal.com/photos/";

    //public String URL_PRODUCT = "http://services.hanselandpetal.com/feeds/flowers.xml";


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
                requestData(URL_PRODUCT);
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
    private class DownloadFlowerNamesTask extends AsyncTask<String, String, List<Flower>>{

        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting task");
            if(tasks.size() == 0)
                pb.setVisibility(View.VISIBLE);
            tasks.add(this);
        }

        @Override
        protected List<Flower> doInBackground(String... params) {
            try {
                //params[0] holds the uri right now.
                String content = HttpManager.getData(params[0], "feeduser", "feedpassword");
                flowerList = FlowersXMLPullParser.parseXML(content);

                //Loop through all flowers, get photo names and download images.
                for (Flower flower: flowerList){
                    try {
                        String imageURL = URL_PHOTO + flower.getPhoto();
                        //get as inout stream
                        InputStream in = (InputStream) new URL(imageURL).getContent();
                        //convert to bitmap
                        Bitmap image = BitmapFactory.decodeStream(in);
                        flower.setImage(image);
                        //close input stream
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return flowerList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Flower> result) {

            tasks.remove(this);
            if(tasks.size() == 0)
                pb.setVisibility(View.INVISIBLE);

            if (result == null)
                Toast.makeText(MainActivity.this, "Could not connect to web service", Toast.LENGTH_LONG).show();
            else
                updateDisplay();

        }
    }

    private void updateDisplay() {
        FlowersListAdapter flowersListAdapter = new FlowersListAdapter(this,R.layout.list_item_flower, flowerList);
        setListAdapter(flowersListAdapter);
        //outputTextView.append(text + "\n" );
    }
}
