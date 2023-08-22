package com.duplo.b2bplatform.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@NoArgsConstructor
@Data
@Configuration
public class B2BConfig {
  @Value("${b2b.tax.url}")
  private String taxLogApiUrl;
  @Value("${b2b.platform.code}")
  private String platformCode;

  @Bean
  public WebClient getWebClient(WebClient.Builder webClientBuilder) {

    return webClientBuilder
            .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
            .baseUrl(getTaxLogApiUrl())
            .build();
  }

  private HttpClient getHttpClient() {
    return HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .doOnConnected(conn -> conn
                    .addHandlerLast(new ReadTimeoutHandler(5))
                    .addHandlerLast(new WriteTimeoutHandler(5)));
  }
}
