package com.interview.notes.y2024.june.test1.controller;

import com.interview.notes.y2024.june.test1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<UnifiedProduct> getProducts(@RequestParam(required = false) String type) {
        return productService.getUnifiedProducts(type);
    }
}