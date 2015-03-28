package android.ankur.com.filestorage_tourslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Ankur on 3/27/2015.
 */
public class ToursListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String fileName;
    private final int resource;

    public ToursListAdapter(Context context, int resource, String fileName) {
        super(context, resource);
        this.context = context;
        this.fileName = fileName;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);
        TextView tour_label = (TextView) rowView.findViewById(R.id.listItem_Label);
        ImageButton tour_icon = (ImageButton) rowView.findViewById(R.id.listItem_Icon);
        //Set text to tour_label
        //Set text to tour_icon


        return super.getView(position, convertView, parent);
    }
}
