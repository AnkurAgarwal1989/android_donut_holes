package android.ankur.com.webservices_flowercatalog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ankur on 3/30/2015.
 */
public class HttpManager {

    //static so it belongs to the class, not an object of the class. we wont need to create an instance.
    public static String getData(String uri) throws IOException {

        URL url = new URL(uri);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
        return readStream(in);
    }

    private static String readStream(InputStreamReader in){

        BufferedReader reader = new BufferedReader(in);
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while( (line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
        }
        return sb.toString();
    }
}