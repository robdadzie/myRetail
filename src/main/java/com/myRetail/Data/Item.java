package com.myRetail.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
  String upc;
  String itemId;
  ProductDescription description;

  public String getItemId() {
    return itemId;
  }

  @JsonProperty(value = "tcin")
  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getUpc() {
    return upc;
  }

  public void setUpc(String upc) {
    this.upc = upc;
  }


  public ProductDescription getDescription() {
    return description;
  }

  @JsonProperty(value = "product_description")
  public void setDescription(ProductDescription description) {
    this.description = description;
  }

}
