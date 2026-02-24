package dev.anuradha.omsorderservice.client;

import dev.anuradha.omsorderservice.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${services.product.url}")
    private String productServiceUrl;

    public ProductResponse getProduct(UUID productId){
        String url = productServiceUrl + "/api/products/" + productId;

        return restTemplate.getForObject(url, ProductResponse.class);

    }
}
