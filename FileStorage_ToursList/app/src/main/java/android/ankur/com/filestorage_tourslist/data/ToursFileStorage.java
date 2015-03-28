package android.ankur.com.filestorage_tourslist.data;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ankur on 3/27/2015.
 */
//Class to handle reading and writing to external storage for Tours File
public class ToursFileStorage {

    Context context;
    private static final String FILENAME = "tours";
    String path;
    private File file;


    public ToursFileStorage(Context context) throws IOException, JSONException {
        this.context = context;

        if (checkExternalStorage())
        {
            File extDir = context.getExternalFilesDir(null);
            path = extDir.getAbsolutePath();
            Toast.makeText(this.context, path, Toast.LENGTH_LONG).show();
            file = new File(extDir, FILENAME);
            createJSONFile();
        }


    }

    /* Checks if external storage is available for read and write */
    private boolean checkExternalStorage() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
            return true;
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            Toast.makeText(this.context, "External Storage is read-only", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this.context, "External Storage unavailable ", Toast.LENGTH_LONG).show();
        }
        return false;

    }

    public void createJSONFile() throws JSONException, IOException {

        if (!checkExternalStorage())
            return;

        JSONArray data = createNewJSONData();

        String text = data.toString();

        FileOutputStream fileOutputStream;
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(text.getBytes());
        fileOutputStream.close();
    }

    public JSONArray readJSONFile() throws IOException, JSONException {
        FileInputStream fileInputStream;
        fileInputStream = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fileInputStream);
        StringBuffer b = new StringBuffer();
        while(bis.available() != 0){
            char c = (char) bis.read();
            b.append(c);
        }

        bis.close();
        fileInputStream.close();

        JSONArray data = new JSONArray(b.toString());

        return data;
    }

    private JSONArray createNewJSONData() throws JSONException {
        JSONArray data = new JSONArray();
        JSONObject tour;

        tour = new JSONObject();
        tour.put("tour", "Salton Sea");
        tour.put("price", 900);
        data.put(tour);

        tour = new JSONObject();
        tour.put("tour", "Death Valley");
        tour.put("price", 600);
        data.put(tour);

        tour = new JSONObject();
        tour.put("tour", "San Francisco");
        tour.put("price", 1200);
        data.put(tour);

        return data;
    }

}
