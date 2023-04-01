package com.noasecond.nekoflora;

import java.io.Serializable;

public class Product implements Serializable {
    String productName;
    double productPrice;
    String productDescription;
    int productDrawableID;

    public Product(String productName, double productPrice, String productDescription, int productDrawableID) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productDrawableID = productDrawableID;
    }

    //Getter
    public String getProductName() {return this.productName;}
    public String getProductDescription() {return this.productDescription;}
    public int getProductDrawableID() {return this.productDrawableID;}
    public double getProductPrice() {return this.productPrice;}

    //Setter
    public void setProductName(String productName) {this.productName = productName;}
    public void setProductDescription(String productDescription) {this.productDescription = productDescription;}
    public void setProductDrawableID(int productDrawableID) {this.productDrawableID = productDrawableID;}
    public void setProductPrice(double productPrice) {this.productPrice = productPrice;}

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                ", productDrawableID='" + productDrawableID + '\'' +
                '}';
    }
}