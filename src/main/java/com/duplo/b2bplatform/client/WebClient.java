package com.duplo.b2bplatform.client;

import com.duplo.b2bplatform.config.B2BConfig;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
public class WebClient {
    private final B2BConfig b2BConfig;
    @Bean
    public org.springframework.web.reactive.function.client.WebClient getWebClient(org.springframework.web.reactive.function.client.WebClient.Builder webClientBuilder) {

        return webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .baseUrl(b2BConfig.getTaxLogApiUrl())
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
