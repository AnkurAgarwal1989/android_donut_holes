package android.ankur.com.webservices_flowercatalog.data;

import android.graphics.Bitmap;

/**
 * Created by Ankur on 3/30/2015.
 */
public class Flower {

    private int productId;
    private String name;
    private String category;
    private String instructions;
    private double price;
    private String photo;
    private Bitmap image;

    public int getProductId() {
        return productId;
    }

    public void setProductID(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return getName();
    }
}
