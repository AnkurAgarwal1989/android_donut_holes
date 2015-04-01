package android.ankur.com.webservices_flowercatalog;

import android.ankur.com.webservices_flowercatalog.data.Flower;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ankur on 3/31/2015.
 */
public class FlowersListAdapter extends ArrayAdapter<Flower> {
    private final Context context;
    private final int resource;

    public FlowersListAdapter(Context context, int resource, List<Flower> flowers) {
        super(context, resource, flowers);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);
        TextView flowerName = (TextView) rowView.findViewById(R.id.textView_flowerName);
        ImageView flowerImage = (ImageView) rowView.findViewById(R.id.imageView_flowerImage);
        Flower flower = getItem(position);
        flowerName.setText(flower.toString());

        Bitmap bitmap = flower.getImage();

        if (bitmap != null)
            flowerImage.setImageBitmap(bitmap);

        return rowView;

    }
}
