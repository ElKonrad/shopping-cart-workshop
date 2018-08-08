package pl.workshops.shoppingcart.cart;

import pl.workshops.shoppingcart.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Bag {

    private String id;
    private List<Item> products;

    Bag(String id) {
        this.id = id;
    }

    String getId() {
        return id;
    }

    List<Item> getProducts() {
        return products;
    }

    void add(Item item) {
        Product product = item.getProduct();
        if (Objects.isNull(products)) {
            products = new ArrayList<>();
        }
        if (product.isNotAvailable() || product.getQuantity() == 0 || item.getQuantity() > product.getQuantity()) {
            throw new IllegalStateException("Product with ID=[" + product.getId() + "] is not available or requested quantities exceeds available stock");
        }
        products.add(item);
    }

    BigDecimal calculateTotalCost() {
        return products.stream()
                       .map(this::calculateOneItemCost)
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    void clear() {
        products.clear();
    }

    private BigDecimal calculateOneItemCost(Item item) {
        BigDecimal requestedQuantity = new BigDecimal(item.getQuantity());
        return item.getProductUnitPrice().multiply(requestedQuantity);
    }
}
