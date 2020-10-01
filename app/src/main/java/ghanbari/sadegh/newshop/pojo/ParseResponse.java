
package ghanbari.sadegh.newshop.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseResponse {

    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ParseResponse() {
    }

    /**
     * 
     * @param createdAt
     * @param objectId
     */
    public ParseResponse(String objectId, String createdAt) {
        super();
        this.objectId = objectId;
        this.createdAt = createdAt;
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

}
