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
            List<Flower> flowers = new ArrayList<Flower>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            //Open required .xml file from res/raw
            InputStream stream = context.getResources().openRawResource(R.raw.tours);
            xpp.setInput(stream, null);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    handleStartTag(xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(xpp.getText());
                }
                eventType = xpp.next();
            }

        } catch (Resources.NotFoundException e) {
            Log.d(LOGTAG, e.getMessage());
        } catch (XmlPullParserException e) {
            Log.d(LOGTAG, e.getMessage());
        } catch (IOException e) {
            Log.d(LOGTAG, e.getMessage());
        }

        return tours;
    }

    private void handleText(String text) {
        String xmlText = text;
        if (currentTour != null && currentTag != null) {
            if (currentTag.equals(TOUR_ID)) {
                Integer id = Integer.parseInt(xmlText);
                currentTour.setId(id);
            }
            else if (currentTag.equals(TOUR_TITLE)) {
                currentTour.setTitle(xmlText);
            }
            else if (currentTag.equals(TOUR_DESC)) {
                currentTour.setDescription(xmlText);
            }
            else if (currentTag.equals(TOUR_IMAGE)) {
                currentTour.setImage(xmlText);
            }
            else if (currentTag.equals(TOUR_PRICE)) {
                double price = Double.parseDouble(xmlText);
                currentTour.setPrice(price);
            }
        }
    }

    private void handleStartTag(String name) {
        if (name.equals("tour")) {
            //create new tour object when you hit <tour>
            currentTour = new Tour();
            tours.add(currentTour);
        }
        else {
            currentTag = name;
        }
    }
}
