package com.interview.notes.y2024.june.test1.client;

import com.interview.notes.y2024.june.test1.client.model.Product;
import com.interview.notes.y2024.june.test1.client.model.ProductPrice;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Set;

@Component
public class ProductsClient {
    private static final String productsURL = "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/sainsbury_products/products_v2.json";
    private static final String productsPriceURL = "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/sainsbury_products/products_price_v2.json";
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductsClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Set<Product> getProducts() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(this.objectMapper)))
                .build();
        WebClient client = WebClient.builder().exchangeStrategies(exchangeStrategies).build();
        Mono<Set<Product>> response = client
                .get()
                .uri(productsURL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Set<Product>>() {})
                .log();
        return response.block();
    }

    public Set<ProductPrice> getProductsPrices() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(this.objectMapper)))
                .build();
        WebClient client = WebClient.builder().exchangeStrategies(exchangeStrategies).build();
        Mono<Set<ProductPrice>> response = client
                .get()
                .uri(productsPriceURL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Set<ProductPrice>>() {})
                .log();
        return response.block();
    }
}
