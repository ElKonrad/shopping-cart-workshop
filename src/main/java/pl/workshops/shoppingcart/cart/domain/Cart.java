package pl.workshops.shoppingcart.cart.domain;

import lombok.Getter;
import pl.workshops.shoppingcart.cart.dto.CartDto;
import pl.workshops.shoppingcart.cart.dto.ItemDto;
import pl.workshops.shoppingcart.product.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

class Cart {

    @Getter
    private String id;
    @Getter
    private List<Item> items = new ArrayList<>();

    Cart(String id) {
        this.id = id;
    }


    CartDto dto() {
        return new CartDto(id, items.stream()
                                    .map(Item::dto)
                                    .collect(toList())
        );
    }

    void add(Item item) {
        ProductDto product = item.getProduct();
        if (product.cannotBeOrder() || item.getQuantity() > product.getQuantity()) {
            throw new IllegalStateException("Product with ID=[" + product.getId() + "] is not canBeOrder or requested quantities exceeds available stock");
        }
        items.add(item);
    }

    BigDecimal calculateTotalCost() {
        return items.stream()
                    .map(this::calculateOneItemCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    void clear() {
        items.clear();
    }

    void remove(ItemDto item) {
        List<Item> toRemove = items.stream()
                                   .filter(i -> i.getId().equals(item.getId()))
                                   .collect(toList());
        items.removeAll(toRemove);
    }

    private BigDecimal calculateOneItemCost(Item item) {
        BigDecimal requestedQuantity = new BigDecimal(item.getQuantity());
        return item.getProductUnitPrice().multiply(requestedQuantity);
    }
}
