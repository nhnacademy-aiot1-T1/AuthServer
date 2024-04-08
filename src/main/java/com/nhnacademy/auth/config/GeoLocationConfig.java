package com.nhnacademy.auth.config;

import com.maxmind.geoip2.DatabaseReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoLocationConfig {

  @Bean("databaseReader")
  public DatabaseReader databaseReader() throws IOException {

    InputStream inputStream = Objects.requireNonNull(
        GeoLocationConfig.class.getClassLoader().getResourceAsStream("database.json"));

    return new DatabaseReader.Builder(inputStream).build();
  }
}
