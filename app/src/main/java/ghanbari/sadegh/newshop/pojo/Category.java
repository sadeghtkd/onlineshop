
package ghanbari.sadegh.newshop.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

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
    @SerializedName("icon")
    @Expose
    private String icon;
    /**
     * No args constructor for use in serialization
     * 
     */
    public Category() {
    }

    /**
     * 
     * @param updatedAt
     * @param title
     * @param createdAt
     * @param objectId
     */
    public Category(String objectId, String title, String createdAt, String updatedAt,String icon) {
        super();
        this.objectId = objectId;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.icon = icon;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
