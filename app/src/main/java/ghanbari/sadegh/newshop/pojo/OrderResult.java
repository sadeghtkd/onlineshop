
package ghanbari.sadegh.newshop.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResult {

    @SerializedName("results")
    @Expose
    private List<Order> results = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderResult() {
    }

    /**
     *
     * @param results
     */
    public OrderResult(List<Order> results) {
        super();
        this.results = results;
    }

    public List<Order> getResults() {
        return results;
    }

    public void setResults(List<Order> results) {
        this.results = results;
    }

}
