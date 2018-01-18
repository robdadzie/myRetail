package com.myRetail.web.controller;

import com.myRetail.core.Domain.ProductDetails;
import com.myRetail.core.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.myRetail.core.Constants.Constants.*;

@RestController
@RequestMapping(value = API_VERSION + PRODUCTS )
@Api(value = API_VERSION + PRODUCTS)
public class ProductController {

  @Autowired
  ProductService productService;

  @ApiOperation(value = "Get Product detail by ID")
  @RequestMapping(value = "{id}",
                  method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ProductDetails getProductDetail(@PathVariable String id) {
    return productService.getProductDetails(id);
  }

  @ApiOperation(value = "Update product price")
  @RequestMapping(value = "{id}",
                  method = RequestMethod.PUT,
                  consumes = MediaType.APPLICATION_JSON_VALUE)
  public void saveOrUpdatePrice(@PathVariable String id, @RequestBody ProductDetails productDetails) {
    productService.saveOrUpdateProductPrice(id, productDetails.getPrice());
  }

}
