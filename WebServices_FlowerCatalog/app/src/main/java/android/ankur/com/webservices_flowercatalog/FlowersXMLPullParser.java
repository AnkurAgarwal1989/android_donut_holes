package android.ankur.com.webservices_flowercatalog;

import android.ankur.com.webservices_flowercatalog.data.Flower;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur on 3/31/2015.
 */
public class FlowersXMLPullParser {

    public static List<Flower> parseXML(String content) {
        //context is required as we need to access the right res folders

        try {

            boolean inDataItemTag = false;

            String currentTagName = "";
            Flower flower = null;
            List<Flower> flowerList = new ArrayList<Flower>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(content));

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = xpp.getName();
                        if (currentTagName.equals("product")) {
                            inDataItemTag = true;
                            flower = new Flower();
                            flowerList.add(flower);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (xpp.getName().equals("product"))
                            inDataItemTag = false;
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && flower != null) {
                            switch (currentTagName) {
                                case "productId":
                                    flower.setProductID(Integer.parseInt(xpp.getText()));
                                    break;
                                case "category":
                                    flower.setCategory(xpp.getText());
                                    break;
                                case "name":
                                    flower.setName(xpp.getText());
                                    break;
                                case "instructions":
                                    flower.setInstructions(xpp.getText());
                                    break;
                                case "price":
                                    flower.setPrice(Double.parseDouble(xpp.getText()));
                                    break;
                                case "photo":
                                    flower.setPhoto(xpp.getText());
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = xpp.next();
            }
            return flowerList;

        } catch (Resources.NotFoundException e) {
            Log.d(MainActivity.LOGTAG, e.getMessage());
        } catch (XmlPullParserException e) {
            Log.d(MainActivity.LOGTAG, e.getMessage());
        } catch (IOException e) {
            Log.d(MainActivity.LOGTAG, e.getMessage());
            return null;
        }
        return null;
    }

}
