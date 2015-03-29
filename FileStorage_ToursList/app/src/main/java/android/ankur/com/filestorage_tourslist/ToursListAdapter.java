package android.ankur.com.filestorage_tourslist;

import android.ankur.com.filestorage_tourslist.data.Tour;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ankur on 3/27/2015.
 */
//Class to provide data to list...Class takes tourName and tourImageName and adds to list
public class ToursListAdapter extends ArrayAdapter<Tour> {

    private final Context context;
    //private final List<Tour> tours;
    private final int resource;

    public ToursListAdapter(Context context, int resource, List<Tour> tours) {
        super(context, resource, tours);
        this.context = context;
        this.resource = resource;
        //this.tours = tours;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);
        TextView tour_label = (TextView) rowView.findViewById(R.id.listItem_Label);
        ImageButton tour_icon = (ImageButton) rowView.findViewById(R.id.listItem_Icon);
        Tour tour = getItem(position);
        tour_label.setText(tour.toString());
        int drawableResourceId = context.getResources().getIdentifier(tour.getImage(), "drawable", context.getPackageName());
        if (drawableResourceId > 0)
            tour_icon.setImageDrawable( context.getResources().getDrawable(drawableResourceId));
        else {
            tour_icon.setImageDrawable(null);
            tour_icon.setVisibility(View.INVISIBLE);
        }
        //Set text to tour_label
        //Set text to tour_icon
        return rowView;
    }
}
