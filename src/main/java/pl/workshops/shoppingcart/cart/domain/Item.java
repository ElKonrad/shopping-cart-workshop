package pl.workshops.shoppingcart.cart.domain;

import lombok.Getter;
import pl.workshops.shoppingcart.cart.dto.ItemDto;
import pl.workshops.shoppingcart.product.dto.ProductDto;

import java.math.BigDecimal;

class Item {

    @Getter
    private String id;
    @Getter
    private int quantity;
    @Getter
    private ProductDto product;

    Item(String id, int quantity, ProductDto product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    BigDecimal getProductUnitPrice() {
        return product.getUnitPrice();
    }

    ItemDto dto() {
        return new ItemDto(id, quantity);
    }
}
