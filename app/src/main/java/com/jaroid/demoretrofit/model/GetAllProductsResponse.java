
package com.jaroid.demoretrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jaroid.demoretrofit.model.Product;

public class GetAllProductsResponse {

    @SerializedName("products")
    @Expose
    private List<Product> products;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetAllProductsResponse() {
    }

    /**
     * 
     * @param total
     * @param limit
     * @param skip
     * @param products
     */
    public GetAllProductsResponse(List<Product> products, Integer total, Integer skip, Integer limit) {
        super();
        this.products = products;
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

}
