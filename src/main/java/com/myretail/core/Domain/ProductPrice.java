package com.myretail.core.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class ProductPrice implements Serializable{
  static final long serialVersionUID = 2L;

  private BigDecimal value;

  @JsonProperty(value = "currencyCode")
  Currency currencyCode;

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }


  public Currency getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(Currency currencyCode) {
    this.currencyCode = currencyCode;
  }

  @Override
  public String toString() {
    return "ProductPrice{" +
           "value=" + value +
           ", currencyCode=" + currencyCode +
           '}';
  }
}
