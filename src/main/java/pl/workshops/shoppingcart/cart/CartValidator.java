package pl.workshops.shoppingcart.cart;

import java.util.UUID;

class CartValidator {

    static void checkId(String cartId) throws IllegalArgumentException {
        UUID.fromString(cartId);
    }
}
