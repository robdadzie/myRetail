package com.myretail.web.controller;

import com.myretail.core.Domain.Product;
import com.myretail.core.Domain.ProductPrice;
import com.myretail.core.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.myretail.core.Constants.Constants.*;

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
  public Product getProductDetail(@PathVariable String id) {
    return productService.getProductDetails(id);
  }

  @ApiOperation(value = "Update product price")
  @RequestMapping(value = "{id}",
                  method = RequestMethod.PUT,
                  consumes = MediaType.APPLICATION_JSON_VALUE)
  public void saveOrUpdatePrice(@PathVariable String id, @RequestBody ProductPrice productPrice) {
    productService.saveOrUpdateProductPrice(id, productPrice);
  }

}
