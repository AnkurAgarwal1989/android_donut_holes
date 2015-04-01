package android.ankur.com.webservices_flowercatalog;

import android.ankur.com.webservices_flowercatalog.data.Flower;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
        Flower flower = getItem(position);
        flowerName.setText(flower.toString());

        Bitmap bitmap = flower.getImage();

        if (bitmap != null) {
            ImageView flowerImage = (ImageView) rowView.findViewById(R.id.imageView_flowerImage);
            flowerImage.setImageBitmap(bitmap);
        }
        else{
            FlowerandView flowerandView = new FlowerandView();
            flowerandView.flower = flower;
            flowerandView.view = rowView;

            new DownloadImage().execute(flowerandView);
            ImageView flowerImage = (ImageView) rowView.findViewById(R.id.imageView_flowerImage);
            flowerImage.setImageBitmap(flower.getImage());
        }

        return rowView;
    }

    //We need to pass the view to the async task so that the display is immediately updated...
    //we need to pass Flower becuase we need to know what image to download
    class FlowerandView {
        public View view;
        public Flower flower;
    }

    //We need to send the view, the flower object and the


    private class DownloadImage extends AsyncTask<FlowerandView, Void, FlowerandView> {

        @Override
        protected FlowerandView doInBackground(FlowerandView... params) {
            FlowerandView container = params[0];
            Flower flower = container.flower;

            try {
                String imageURL = MainActivity.URL_PHOTO + flower.getPhoto();
                //get as inout stream
                InputStream in = (InputStream) new URL(imageURL).getContent();
                //convert to bitmap
                Bitmap image = BitmapFactory.decodeStream(in);
                flower.setImage(image);
                //close input stream
                in.close();
                return container;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(FlowerandView flowerandView) {
            ImageView imageView = (ImageView) flowerandView.view.findViewById(R.id.imageView_flowerImage);
            imageView.setImageBitmap(flowerandView.flower.getImage());
        }
    }
}
