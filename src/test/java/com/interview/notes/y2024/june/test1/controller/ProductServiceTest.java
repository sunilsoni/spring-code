package com.interview.notes.y2024.june.test1.controller;

import com.interview.notes.y2024.june.test1.client.ProductsClient;
import com.interview.notes.y2024.june.test1.client.model.Product;
import com.interview.notes.y2024.june.test1.client.model.ProductPrice;
import com.interview.notes.y2024.june.test1.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductsClient productsClient;

    @Test
    void testGetUnifiedProducts() {
        Set<Product> products = new HashSet<>();
        products.add(new Product(1, "BASIC", "Product 1", "http://example.com/product1"));
        products.add(new Product(2, "PREMIUM", "Product 2", "http://example.com/product2"));

        Set<ProductPrice> prices = new HashSet<>();
        prices.add(new ProductPrice(1, 10.0, "kg", 1));
        prices.add(new ProductPrice(2, 20.0, "g", 100));

        when(productsClient.getProducts()).thenReturn(products);
        when(productsClient.getProductsPrices()).thenReturn(prices);

        List<UnifiedProduct> result = productService.getUnifiedProducts(null);
        assertEquals(2, result.size());

        result = productService.getUnifiedProducts("BASIC");
        assertEquals(1, result.size());
        assertEquals("Product 1", result.get(0).getName());
    }

    @Test
    void testGetUnifiedProductsWithNoTypeMatch() {
        Set<Product> products = new HashSet<>();
        products.add(new Product(1, "BASIC", "Product 1", "http://example.com/product1"));
        products.add(new Product(2, "PREMIUM", "Product 2", "http://example.com/product2"));

        Set<ProductPrice> prices = new HashSet<>();
        prices.add(new ProductPrice(1, 10.0, "kg", 1));
        prices.add(new ProductPrice(2, 20.0, "g", 100));

        when(productsClient.getProducts()).thenReturn(products);
        when(productsClient.getProductsPrices()).thenReturn(prices);

        List<UnifiedProduct> result = productService.getUnifiedProducts("NON_EXISTENT_TYPE");
        assertEquals(0, result.size());
    }
}
