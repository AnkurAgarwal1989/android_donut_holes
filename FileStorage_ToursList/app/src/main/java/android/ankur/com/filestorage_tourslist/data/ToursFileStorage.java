package android.ankur.com.filestorage_tourslist.data;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Ankur on 3/27/2015.
 */
//Class to handle reading and writing to external storage for Tours File
public class ToursFileStorage {

    Context context;

    public ToursFileStorage(Context context){
        this.context = context;

        if (checkExternalStorage())
        {
            File f = context.getExternalFilesDir(null);
            String path = f.getAbsolutePath();
            Toast.makeText(this.context, path, Toast.LENGTH_LONG).show();
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
            return false;
        }
        else {
            Toast.makeText(this.context, "External Storage unavailable ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;

    }

   // public St

}
