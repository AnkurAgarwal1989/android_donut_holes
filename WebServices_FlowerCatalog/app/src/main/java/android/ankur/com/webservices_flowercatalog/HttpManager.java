
package android.ankur.com.webservices_flowercatalog;

import android.util.Base64;
import android.util.Log;

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

    private static HttpURLConnection urlConnection;
    private static URL url;


    //static so it belongs to the class, not an object of the class. we wont need to create an instance.
    public static String getData(String uri) throws IOException {

        url = new URL(uri);

        if (openUnsecureConnection() == 1){
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            return readStream(in);
        }
        else
            return String.valueOf(-1);
    }

    //returning ints to show what error occured..like 401  feed not found and all.
    private static int openUnsecureConnection(){
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            try {
                int status = urlConnection.getResponseCode();
                return status;
            } catch (IOException e1) {
                e1.printStackTrace();
                return -1;
            }
        }
        return 1;
    }
    //Overload to accept 3 params when using secure url
    public static String getData(String uri, String username, String password) throws IOException {

        url = new URL(uri);
        Log.i(MainActivity.LOGTAG, "getData with username and password");

        byte[] loginBytes = (username + ":" + password).getBytes();
        StringBuilder loginBuilder = new StringBuilder();
        // important stuff
        loginBuilder.append("Basic ");
        loginBuilder.append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        Log.i(MainActivity.LOGTAG, "String builder " + loginBuilder.toString());

        if (openSecureConnection(loginBuilder.toString()) == 1){

            Log.i(MainActivity.LOGTAG, "Creating inputstream");

            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            return readStream(in);
        }
        else
            return String.valueOf(-1);
    }

    private static int openSecureConnection(String login){
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("Authorization", login);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                int status = urlConnection.getResponseCode();
                Log.i(MainActivity.LOGTAG, "Return status : " + String.valueOf(status));
                return status;
            } catch (IOException e1) {
                e1.printStackTrace();
                return -1;
            }
        }
        return 1;
    }

    private static String readStream(InputStreamReader in){

        Log.i(MainActivity.LOGTAG, "reading ");

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
        //Log.i(MainActivity.LOGTAG, sb.toString());
        return sb.toString();
    }
}
