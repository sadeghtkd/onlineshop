
package ghanbari.sadegh.newshop.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResult {

    @SerializedName("results")
    @Expose
    private List<Category> results = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CategoryResult() {
    }

    /**
     * 
     * @param results
     */
    public CategoryResult(List<Category> results) {
        super();
        this.results = results;
    }

    public List<Category> getResults() {
        return results;
    }

    public void setResults(List<Category> results) {
        this.results = results;
    }

}
