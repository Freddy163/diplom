package com.example.fishop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Модель корзины
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        // Проверяем, есть ли уже такой товар в корзине
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void updateQuantity(Long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    items.remove(item);
                }
                break;
            }
        }
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
