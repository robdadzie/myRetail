package com.myRetail.core.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDescription {
  String title;
  String generalDescription;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getGeneralDescription() {
    return generalDescription;
  }

  @JsonProperty(value = "general_description")
  public void setGeneralDescription(String generalDescription) {
    this.generalDescription = generalDescription;
  }

}
