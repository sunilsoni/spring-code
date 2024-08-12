package com.interview.notes.y2024.june.test3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.ShopApplication.models.Cart;
import com.shop.ShopApplication.models.Catalog;
import com.shop.ShopApplication.models.Product;

import jakarta.annotation.PostConstruct;

/**
 * Service class to handle shopping cart operations.
 */
@Service
public class ShoppingCartService {

    private Catalog catalog;
    private Cart cart;

    @PostConstruct
    public void init() {
        // Load products.json
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream("/products.json")) {
            List<Product> products = mapper.readValue(is, new TypeReference<List<Product>>() {});
            catalog = new Catalog(products);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load products.json", e);
        }
        this.cart = new Cart();
    }

    public int getCatalogSize() {
        return catalog.getProducts().size();
    }

    public Product getProductFromCatalogById(String id) {
        return catalog.getProducts().stream()
            .filter(product -> product.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public boolean addProductToCartById(String id) {
        Product product = getProductFromCatalogById(id);
        if (product != null && product.getQuantity() > 0) {
            // Decrease quantity in the catalog before adding to cart
            product.setQuantity(product.getQuantity() - 1);
            cart.addProduct(product);
            return true;
        }
        return false;
    }

    public boolean removeProductFromCartById(String id) {
        Product product = cart.getProducts().stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
        if (product != null) {
            // Increase quantity back to catalog after removal from cart
            Product catalogProduct = getProductFromCatalogById(product.getId());
            if (catalogProduct != null) {
                catalogProduct.setQuantity(catalogProduct.getQuantity() + 1);
            }
            cart.removeProduct(product);
            return true;
        }
        return false;
    }

    public Cart getShoppingCart() {
        return cart;
    }

    public Product getItemFromCartById(String id) {
        return cart.getProducts().stream()
            .filter(product -> product.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public Cart checkout() {
        if (cart.getProducts().isEmpty()) {
            return null;
        }
        Cart checkedOutCart = new Cart();
        checkedOutCart.setProducts(new ArrayList<>(cart.getProducts()));
        cart.getProducts().clear(); // Clear the shopping cart after checkout
        return checkedOutCart;
    }
}
