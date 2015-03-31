package android.ankur.com.webservices_flowercatalog.data;

import android.graphics.Bitmap;

/**
 * Created by Ankur on 3/30/2015.
 */
public class Flower {

    private int productID;
    private String name;
    private String imageName;
    private Bitmap image;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
