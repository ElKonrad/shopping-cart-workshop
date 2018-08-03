package pl.workshops.shoppingcart.cart;

import pl.workshops.shoppingcart.product.Product;

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
        this.product = product;
    }

    boolean isSufficientQuantitiesInStock() {
        return this.quantity < product.getQuantity();
    }
}
