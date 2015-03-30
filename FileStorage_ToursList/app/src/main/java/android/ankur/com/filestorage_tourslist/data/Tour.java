package android.ankur.com.filestorage_tourslist.data;

import android.ankur.com.filestorage_tourslist.MainActivity;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.NumberFormat;

public class Tour implements Parcelable{
	private long id;
	private String title;
	private String description;
	private double price;
	private String image;

    public Tour(){
    }

    ///Required for Parcelable
    //Deafult constructor used when packing data
    public Tour (Parcel in){
        Log.i(MainActivity.LOGTAG, "Parcel constructor");

        //This order matters
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        price = in.readDouble();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i(MainActivity.LOGTAG, "Writing to Parcel");

        //Order of passing data in and out has to be same
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(image);
    }

    public static final Creator<Tour> CREATOR = new Creator<Tour>() {
        @Override
        public Tour createFromParcel(Parcel source) {
            Log.i(MainActivity.LOGTAG, "Create from Parcel");
            //Create Tour object with Parcel constructor
            return new Tour(source);
        }

        @Override
        public Tour[] newArray(int size) {
            Log.i(MainActivity.LOGTAG, "newArray");
            return new Tour[size];
        }
    };
    // Required for Parcelable

    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return title + "\n(" + nf.format(price) + ")";
    }
}
