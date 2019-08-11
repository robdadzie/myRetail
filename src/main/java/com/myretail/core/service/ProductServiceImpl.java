package com.myretail.core.service;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.myretail.core.Dao.PriceDao;
import com.myretail.core.Domain.FieldType;
import com.myretail.core.Domain.ProductPrice;
import com.myretail.core.Domain.Product;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.myretail.core.Constants.Constants.*;
import static org.apache.commons.lang3.reflect.FieldUtils.*;

@Service
@SuppressWarnings("unchecked")
public class ProductServiceImpl implements ProductService {
  private static RestTemplate restTemplate = new RestTemplate();
  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
  private Map<String, String> fieldMappings;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  PriceDao priceDao;

  @Value("${product.url}")
  private String productUrl;

  @Override
  public void saveOrUpdateProductPrice(String id, ProductPrice price) {
    priceDao.saveOrUpdatePrice(id, price);
  }

  @Override
  public Product getProductDetails(String id) {
    Map<String, String> urlParams = new HashMap<>();
    urlParams.put(PRODUCT_ID, id);

    String  resp = restTemplate.getForObject(productUrl, String.class, urlParams);
    Product p    = new Product();
    try {
      JSONObject jsonObject = new JSONObject(resp);
      if (jsonObject.get(PRODUCT) != null) {
        p = extractProduct((JSONObject) jsonObject.get(PRODUCT));
        if (StringUtils.isNotBlank(p.getId())) {
          p.setPrice(priceDao.findPriceByProductId(id));
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return p;
  }

  @Override
  public ProductPrice getProductPrice(String id) {
    return priceDao.findPriceByProductId(id);
  }

  private Product extractProduct(JSONObject jsonObject) {
    Product product = new Product();
    fieldMappings = (Map<String, String>) redisTemplate.opsForHash().get(FIELD_MAPPINGS, PRODUCT);

    if (MapUtils.isNotEmpty(fieldMappings)) {
      setProductFields(product, jsonObject, fieldMappings);
    }
    return product;
  }


  private void setProductFields(Product product, JSONObject jsonObject, Map<String, String> fieldMap) {
    if (jsonObject != null && product != null && MapUtils.isNotEmpty(fieldMap)) {
      JsonFlattener flattener = new JsonFlattener(jsonObject.toString());
      Map<String, Object> responseObjectMap = flattener.flattenAsMap();

      Map<String, Object> result = responseObjectMap.entrySet()
                                                    .stream()
                                                    .filter(f -> fieldMap.containsKey(f.getKey().toLowerCase()))
                                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

      Map<String, Object> unMapped = responseObjectMap.entrySet()
                                                      .stream()
                                                      .filter(f -> !fieldMap.containsKey(f.getKey().toLowerCase()))
                                                      .collect(
                                                          Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

      for (Map.Entry<String, String> f : fieldMap.entrySet()) {
        try {
          String respVal      = result.get(f.getKey()).toString();
          String pdtFieldName = f.getValue();
          product.setOtherInfo(unMapped);

          Field field = FieldUtils.getField(product.getClass(), pdtFieldName, true);
          if (field != null) {
            Object  value      = null;
            boolean writeValue = true;

            switch (FieldType.fromName(field.getType().getName())) {
              case INT:
                value = Integer.parseInt(respVal);
                break;
              case LOCAL_DATE:
                DateTimeFormatter
                    inputFormatter = DateTimeFormatter.ofPattern(DATE_READER_FORMAT, Locale.ENGLISH);
                value = LocalDate.parse(respVal, inputFormatter);
                break;
              case URL:
                value = new URL(respVal);
                break;
              case STRING:
                value = respVal;
                break;
              default:
                writeValue = false;
                break;
            }

            if (value != null && writeValue) {
              writeDeclaredField(product, pdtFieldName, value, true);
            } else {
              LOG.info(String.format("Field Type [%s], cannot be parsed.", field.getType().getName()));
            }
          } else {
            LOG.warn(String.format("[%s], is not a valid field.", pdtFieldName));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
