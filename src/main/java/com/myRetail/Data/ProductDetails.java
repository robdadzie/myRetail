package com.myRetail.Data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDetails {
  String id;
  String name;

  @JsonProperty(value = "current_price")
  Price  price;

  public ProductDetails() {
  }

  public ProductDetails(Product product, Price price) {
    this.id = product.getItem().getItemId();
    this.name = product.getItem().getDescription().getTitle();
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Price getPrice() {
    return price;
  }

  public void setPrice(Price price) {
    this.price = price;
  }

}
