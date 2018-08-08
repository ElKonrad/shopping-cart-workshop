package pl.workshops.shoppingcart.cart;

import pl.workshops.shoppingcart.product.Product;

import java.math.BigDecimal;
import java.util.Objects;

class Item {

    private String productId;
    private int quantity;
    private Product product;

    Item(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    String getProductId() {
        return productId;
    }

    int getQuantity() {
        return quantity;
    }

    Product getProduct() {
        return product;
    }

    void setProduct(Product product) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("Product can not be a null");
        }
        this.product = product;
    }

    boolean isNotEnoughQuantitiesInStock() {
        return this.quantity > product.getQuantity();
    }

    BigDecimal getProductUnitPrice() {
        return product.getUnitPrice();
    }
}
