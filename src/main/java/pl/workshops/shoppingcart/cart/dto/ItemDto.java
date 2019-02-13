package pl.workshops.shoppingcart.cart.dto;

import lombok.Getter;

@Getter
public class ItemDto {

    private String id;
    private int quantity;

    public ItemDto(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
