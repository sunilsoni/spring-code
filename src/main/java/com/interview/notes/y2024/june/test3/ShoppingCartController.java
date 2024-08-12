package com.interview.notes.y2024.june.test3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.ShopApplication.models.Cart;
import com.shop.ShopApplication.models.Product;
import com.shop.ShopApplication.services.ShoppingCartService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api") // General base route for the APIs
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/catalog/size")
    public ResponseEntity<Map<String, Object>> getCatalogSize() {
        int size = shoppingCartService.getCatalogSize();
        return ResponseEntity.ok(Map.of("success", true, "count", size));
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<Map<String, Object>> getProductFromCatalog(@PathVariable String id) {
        Product product = shoppingCartService.getProductFromCatalogById(id);
        if (product == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "Product not found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(Map.of("success", true, "product", product));
    }

    @GetMapping("/cart")
    public ResponseEntity<Map<String, Object>> getShoppingCart() {
        Cart cart = shoppingCartService.getShoppingCart();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "products", cart.getProducts(),
            "totalCost", cart.getTotalCost()
        ));
    }

    @GetMapping("/cart/item/{id}")
    public ResponseEntity<Map<String, Object>> getItemFromCart(@PathVariable String id) {
        Product product = shoppingCartService.getItemFromCartById(id);
        if (product == null) {
            return new ResponseEntity<>(Map.of("success", false, "message", "Item not found in cart"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(Map.of("success", true, "product", product));
    }

    @PostMapping("/cart/item/{id}")
    public ResponseEntity<Map<String, Object>> addItemToCart(@PathVariable String id) {
        boolean success = shoppingCartService.addProductToCartById(id);
        if (!success) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("success", false, "message", "Product not found or out of stock"));
        }
        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping("/cart/item/{id}")
    public ResponseEntity<Map<String, Object>> removeItemFromCart(@PathVariable String id) {
        boolean success = shoppingCartService.removeProductFromCartById(id);
        if (!success) {
            return new ResponseEntity<>(Map.of("success", false, "message", "Item not found in cart"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<Map<String, Object>> checkout() {
        Cart checkedOutCart = shoppingCartService.checkout();
        if (checkedOutCart == null || checkedOutCart.getProducts().isEmpty()) {
            return new ResponseEntity<>(Map.of("success", false, "message", "Cart is empty"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "products", checkedOutCart.getProducts(),
            "totalCost", checkedOutCart.getTotalCost()
        ));
    }
}
