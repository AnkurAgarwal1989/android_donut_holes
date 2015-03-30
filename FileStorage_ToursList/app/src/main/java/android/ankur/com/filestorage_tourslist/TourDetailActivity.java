package android.ankur.com.filestorage_tourslist;

import android.ankur.com.filestorage_tourslist.data.Tour;
import android.ankur.com.filestorage_tourslist.db.ToursMyDataSource;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class TourDetailActivity extends Activity {

    Tour tour;
    boolean isMyTour;
    ToursMyDataSource myDataSource;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        Log.i(MainActivity.LOGTAG, "Detail Activity");

        Bundle bundle = getIntent().getExtras();
        tour = bundle.getParcelable(".data.Tour");

        isMyTour = bundle.getBoolean("isMyTours");

        refreshDisplay();
        myDataSource = new ToursMyDataSource(this);
        myDataSource.openDB();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_tour_detail, menu);

        //if my tour. no add allowed...it is already in your list homie
        menu.findItem(R.id.action_add).setVisible(!isMyTour);

        // if my tour...allow removal
        //Allow removal only when coming from mytours
        menu.findItem(R.id.action_remove).setVisible(isMyTour);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()){

            case R.id.action_add:
                if (myDataSource.addToMyTours(tour))
                    Toast.makeText(this, "Tour Added to your list", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Tour could not be added. It maybe already present in your list", Toast.LENGTH_LONG).show();
                break;

            case R.id.action_remove:
                if (myDataSource.removeFromMyTours(tour)) {
                    Toast.makeText(this, "Tour removed from your list", Toast.LENGTH_LONG).show();
                    //could be any number...our code is -1...which means 1 less row...
                    setResult(-1);
                    finish();
                    //item.
                    //menu.findItem(R.id.action_add).setVisible(true);

                }
                else
                    Toast.makeText(this, "Tour could not be removed. It maybe already present in your list", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshDisplay(){
        TextView tourTitle = (TextView) findViewById(R.id.tourTitle);
        tourTitle.setText(tour.getTitle());

        TextView tourPrice = (TextView) findViewById(R.id.tourPrice);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        tourPrice.setText(nf.format(tour.getPrice()));

        TextView tourDesc = (TextView) findViewById(R.id.tourDesc);
        tourDesc.setText(tour.getDescription());

        ImageView tourImage = (ImageView) findViewById(R.id.tourImage);
        int imageResID = getResources().getIdentifier(tour.getImage(), "drawable", getPackageName());
        if (imageResID != 0)
            tourImage.setImageResource(imageResID);
    }

}
