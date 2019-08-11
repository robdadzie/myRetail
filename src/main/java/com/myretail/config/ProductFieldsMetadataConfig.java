package com.myretail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * The purpose of this config class is to read in field mappings from the application's properties file and persist.
 * These mappings would then be used to transform the JSON result obtained from the 'redsky' API into a product object.
 * This approach lends to ease of maintenance of the application, if say JSON[field 1] maps to Product[field 3] currently,
 * and there's a need to update this mapping so that JSON[field 3] now maps to Product[field 3], then an update to the properties
 * and a restart of the application is all that's required to effect this change.
 */
@Configuration
public class ProductFieldsMetadataConfig {
  @Autowired
  private RedisTemplate redisTemplate;

  @Value("#{${product}}")
  private Map<String, String> fieldMappings = new HashMap<>();

  @PostConstruct
  private void init() {
    redisTemplate.opsForHash().put("fieldMappings", "product", this.fieldMappings);
  }

  public void setFieldMappings(Map<String, String> fieldMappings) {
    this.fieldMappings = fieldMappings;
  }
}
