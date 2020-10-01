
package ghanbari.sadegh.newshop.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("catId")
    @Expose
    private String catId;
    @SerializedName("banner")
    @Expose
    private String banner;
    /**
     * No args constructor for use in serialization
     * 
     */
    public Product() {
    }

    /**
     * 
     * @param picture
     * @param updatedAt
     * @param title
     * @param price
     * @param description
     * @param createdAt
     * @param objectId
     * @param catId
     */
    public Product(String objectId, String title, String createdAt, String updatedAt, String description, Integer price, String picture, String catId, String banner) {
        super();
        this.objectId = objectId;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.catId = catId;
        this.banner = banner;
    }

    protected Product(Parcel in) {
        objectId = in.readString();
        title = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        picture = in.readString();
        catId = in.readString();
        banner = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(title);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(description);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
        dest.writeString(picture);
        dest.writeString(catId);
        dest.writeString(banner);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
