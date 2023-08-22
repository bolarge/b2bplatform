package com.duplo.b2bplatform.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
public class B2BConfig {
  @Value("${b2b.tax.url}")
  private String taxLogApiUrl;
  @Value("${b2b.platform.code}")
  private String platformCode;

}
