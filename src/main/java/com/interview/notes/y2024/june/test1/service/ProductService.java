package com.interview.notes.y2024.june.test1.service;

import com.interview.notes.y2024.june.test1.client.ProductsClient;
import com.interview.notes.y2024.june.test1.client.model.Product;
import com.interview.notes.y2024.june.test1.client.model.ProductPrice;
import com.interview.notes.y2024.june.test1.controller.UnifiedProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    private final ProductsClient productsClient;

    @Autowired
    public ProductService(ProductsClient productsClient) {
        this.productsClient = productsClient;
    }

    public List<UnifiedProduct> getUnifiedProducts(String type) {
        Set<Product> products = productsClient.getProducts();
        Set<ProductPrice> productPrices = productsClient.getProductsPrices();

        Map<Integer, ProductPrice> priceMap = productPrices.stream()
                .collect(Collectors.toMap(ProductPrice::getProductUid, pp -> pp));

        List<UnifiedProduct> unifiedProducts = products.stream()
                .map(product -> {
                    ProductPrice price = priceMap.get(product.getProductUid());
                    if (price != null) {
                        return new UnifiedProduct(
                                product.getProductUid(),
                                product.getProductType(),
                                product.getName(),
                                product.getFullUrl(),
                                price.getUnitPrice(),
                                price.getUnitPriceMeasure(),
                                price.getUnitPriceMeasureAmount()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (type != null && !type.isEmpty()) {
            unifiedProducts = unifiedProducts.stream()
                    .filter(p -> type.equalsIgnoreCase(p.getProductType()))
                    .collect(Collectors.toList());
        }

        return unifiedProducts;
    }
}
