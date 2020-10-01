
package ghanbari.sadegh.newshop.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileResult {

    @SerializedName("results")
    @Expose
    private List<UserProfile> results = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserProfileResult() {
    }

    /**
     *
     * @param results
     */
    public UserProfileResult(List<UserProfile> results) {
        super();
        this.results = results;
    }

    public List<UserProfile> getResults() {
        return results;
    }

    public void setResults(List<UserProfile> results) {
        this.results = results;
    }

}
