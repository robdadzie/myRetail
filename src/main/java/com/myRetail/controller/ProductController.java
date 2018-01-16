package com.myRetail.controller;

import com.myRetail.Data.ProductDetails;
import com.myRetail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  ProductService productService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ProductDetails getProductDetail(@PathVariable String id) {
    return productService.getProductDetails(id);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public void saveOrUpdatePrice(@PathVariable String id, @RequestBody ProductDetails productDetails) {
    productService.saveOrUpdateProductPrice(id, productDetails.getPrice());
  }

}
