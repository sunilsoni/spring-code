package com.interview.notes.y2024.june.test1.client.model;

public class Product {
    private int productUid;
    private String productType;
    private String name;
    private String fullUrl;

    public Product() {
    }

    public Product(int productUid, String productType, String name, String fullUrl) {
        this.productUid = productUid;
        this.productType = productType;
        this.name = name;
        this.fullUrl = fullUrl;
    }

    public int getProductUid() {
        return productUid;
    }

    public void setProductUid(int productUid) {
        this.productUid = productUid;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return productUid == product.productUid;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(productUid);
    }
}
