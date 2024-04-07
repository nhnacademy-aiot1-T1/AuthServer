package com.nhnacademy.auth.config;

import com.maxmind.geoip2.DatabaseReader;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoLocationConfig {

  @Bean("databaseReader")
  public DatabaseReader databaseReader() throws IOException {

    return new DatabaseReader.Builder(new File(
        Objects.requireNonNull(getClass().getClassLoader().getResource("GeoLite2-Country.mmdb")).getFile())).build();
  }
}
