package com.cart.product.service;

import com.cart.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Value("${fakestore.base-url}")
    private String baseUrl;

    private final WebClient webClient = WebClient.builder().build();

    public List<ProductDto> getAllProducts() {
        return webClient.get()
                .uri(baseUrl + "/products")
                .retrieve()
                .bodyToFlux(ProductDto.class)
                .collectList()
                .block();
    }

    public ProductDto getProductById(Long id) {
        return webClient.get()
                .uri(baseUrl + "/products/" + id)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }
}
