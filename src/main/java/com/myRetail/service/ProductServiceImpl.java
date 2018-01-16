package com.myRetail.service;

import com.myRetail.Dao.PriceDao;
import com.myRetail.Data.Price;
import com.myRetail.Data.Product;
import com.myRetail.Data.ProductDetails;
import com.myRetail.Data.ProductInfoWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class ProductServiceImpl implements ProductService {
    private static RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    PriceDao priceDao;

    @Value("${product.url}")
    private String productUrl;

    @Override
    public void saveOrUpdateProductPrice(String id, Price price){
     priceDao.saveOrUpdatePrice(id, price);
    }

    @Override
    public ProductDetails getProductDetails(String id){
        ProductDetails productDetails = new ProductDetails();
        try {
            Product productInfo = getProductInformation(id);
            Price priceInfo = getProductPrice(id);

            if(productInfo != null) {
             productDetails = new ProductDetails(productInfo, priceInfo);
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return productDetails;
    }

    @Override
    public Price getProductPrice(String id){
        return priceDao.findPriceByProductId(id);
    }

    public Product getProductInformation(String id){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("productId", id);

        ProductInfoWrapper resp = restTemplate.getForObject(getProductUrl(), ProductInfoWrapper.class, urlParams);
        return resp.getProduct();
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

}
