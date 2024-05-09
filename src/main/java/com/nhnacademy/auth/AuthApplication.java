package com.nhnacademy.auth;

import com.nhnacademy.auth.properties.PropertiesMaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@ConfigurationPropertiesScan(basePackageClasses = PropertiesMaker.class)
@EnableEurekaClient

@EnableFeignClients
@SpringBootApplication
public class AuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthApplication.class, args);
  }
}
