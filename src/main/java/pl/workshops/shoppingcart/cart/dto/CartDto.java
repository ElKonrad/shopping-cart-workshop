package pl.workshops.shoppingcart.cart.dto;

import java.util.List;

public class CartDto {

    private String id;
    private List<ItemDto> items;

    public CartDto(String id, List<ItemDto> items) {
        this.id = id;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public List<ItemDto> getItems() {
        return items;
    }
}
