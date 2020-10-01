
package ghanbari.sadegh.newshop.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pic")
    @Expose
    private byte[] pic;
    @SerializedName("userId")
    @Expose
    private String userId;
    /**
     * No args constructor for use in serialization
     *
     */
    public UserProfile() {
    }



    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
