package android.ankur.com.webservices_flowercatalog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankur on 4/1/2015.
 */
//We need to send data to AsyncTask hence the package.
public class RequestPackage {

    private String uri;
    private String method = "GET";

    //An HTTP request can have as many parameters as you want. Each is a key value pair.
    private Map<String, String> requestParams = new HashMap<>();


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    //returns params encoded in a string
    public String getEncodedParams(){
        StringBuilder sb = new StringBuilder();
        for (String key: requestParams.keySet()){
            String value = null; //we need to encode this to take care of special characters
            try {
                value = URLEncoder.encode(requestParams.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //For each entry after first..add & sign
            //for first entry...size of sb == 0
            if (sb.length() > 0)
                sb.append("&");

            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        return sb.toString();
    }

    //This method will set all params
    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    //overload This method can set params one by one. we give strings, this maps them
    public void setRequestParams(String key, String value) {
        requestParams.put(key, value);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }



}
