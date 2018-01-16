package com.myRetail.Data;

public class ProductInfoWrapper {
  Product product;

  public ProductInfoWrapper() {
  }

  public ProductInfoWrapper(Product p) {
    this.product = p;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
