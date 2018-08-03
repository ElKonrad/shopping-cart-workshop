package pl.workshops.shoppingcart.cart;

import pl.workshops.shoppingcart.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bag {

    private String id;
    private List<Item> products;

    public Bag(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Item> getProducts() {
        return products;
    }

    public void add(Item item) {
        Product product = item.getProduct();
        if (Objects.isNull(products)) {
            products = new ArrayList<>();
        }
        if (product.isNotAvailable() || product.getQuantity() == 0 || item.getQuantity() > product.getQuantity()) {
            throw new IllegalStateException("Product with ID=[" + product.getId() + "] is not available or requested quantities exceeds available stock");
        }
        products.add(item);
    }

    public BigDecimal calculateTotalCost() {
        return products.stream()
                       .map(this::calculateOneItemCost)
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateOneItemCost(Item item) {
        return item.getProduct().getUnitPrice().multiply(new BigDecimal(item.getQuantity()));
    }

    public void clear() {
        products.clear();
    }
}
