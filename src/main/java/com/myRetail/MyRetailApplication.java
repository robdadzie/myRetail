package com.myRetail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
                     @PropertySource(value = "classpath:application.properties")
                 })

@SpringBootApplication
public class MyRetailApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyRetailApplication.class, args);
  }

  @Bean
  public EmbeddedServletContainerCustomizer containerCustomizer() {
    return (container -> {
      container.setSessionTimeout(1);
    });
  }
}
