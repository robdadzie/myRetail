package com.myretail.core.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;

public class Product {
  private int stockQuantity;

  private String id;
  private String upc;
  private String title;
  private String brand;
  private String category;
  private String productClass;
  private String countryOfOrigin;

  private URL buyUrl;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, Object> otherInfo;

  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDate streetDate;
  @JsonProperty(value = "price")
  ProductPrice price;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public String getUpc() {
    return upc;
  }

  public void setUpc(String upc) {
    this.upc = upc;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getProductClass() {
    return productClass;
  }

  public void setProductClass(String productClass) {
    this.productClass = productClass;
  }

  public String getCountryOfOrigin() {
    return countryOfOrigin;
  }

  public void setCountryOfOrigin(String countryOfOrigin) {
    this.countryOfOrigin = countryOfOrigin;
  }

  public URL getBuyUrl() {
    return buyUrl;
  }

  public void setBuyUrl(URL buyUrl) {
    this.buyUrl = buyUrl;
  }

  public Map<String, Object> getOtherInfo() {
    return otherInfo;
  }

  public void setOtherInfo(Map<String, Object> otherInfo) {
    this.otherInfo = otherInfo;
  }

  public LocalDate getStreetDate() {
    return streetDate;
  }

  public void setStreetDate(LocalDate streetDate) {
    this.streetDate = streetDate;
  }

  public ProductPrice getPrice() {
    return price;
  }

  public void setPrice(ProductPrice price) {
    this.price = price;
  }
}
