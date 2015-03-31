package android.ankur.com.webservices_flowercatalog;

import android.ankur.com.webservices_flowercatalog.data.Flower;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur on 3/31/2015.
 */
public class FlowersJSONParser {

    public static List<Flower> parseJSON(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<Flower> flowerList = new ArrayList<>();

            for (int i=0; i< ar.length(); i++ ){
                JSONObject obj = null;
                obj = ar.getJSONObject(i);

                Flower flower = new Flower();

                flower.setProductId(obj.getInt("productId"));
                flower.setCategory(obj.getString("category"));
                flower.setName(obj.getString("name"));
                flower.setInstructions(obj.getString("instructions"));
                flower.setPrice(obj.getDouble("price"));
                flower.setPhoto(obj.getString("photo"));

                flowerList.add(flower);
            }
            return flowerList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
