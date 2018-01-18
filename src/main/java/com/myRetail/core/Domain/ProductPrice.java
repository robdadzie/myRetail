package com.myRetail.core.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class ProductPrice implements Serializable{
  static final long serialVersionUID = 2L;

  BigDecimal value;

  @JsonProperty(value = "currency_code")
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
}
