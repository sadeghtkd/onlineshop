
package ghanbari.sadegh.newshop.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("status")
    @Expose
    private String status;
    /**
     * No args constructor for use in serialization
     * 
     */
    public Order() {
    }

    /**
     * 
     * @param createdAt
     * @param productId
     * @param count
     * @param userId
     * @param objectId
     * @param updatedAt
     */
    public Order(String objectId, String userId, String createdAt, String updatedAt, String productId, Integer count,String status) {
        super();
        this.objectId = objectId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.productId = productId;
        this.count = count;
        this.status = status;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
