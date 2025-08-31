package com.airtribe.news_aggregator.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientUtil {

    public final WebClient webClient;

    public WebClientUtil(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> Mono<T> get(String url, Class<T> responseType) {
        return webClient.get().uri(url).retrieve().bodyToMono(responseType);
    }
}
