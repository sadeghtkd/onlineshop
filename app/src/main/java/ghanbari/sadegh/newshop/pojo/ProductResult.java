
package ghanbari.sadegh.newshop.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductResult {

    @SerializedName("results")
    @Expose
    private List<Product> products = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductResult() {
    }

    /**
     * 
     * @param products
     */
    public ProductResult(List<Product> products) {
        super();
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
