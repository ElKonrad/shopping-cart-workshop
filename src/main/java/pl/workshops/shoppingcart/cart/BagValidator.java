package pl.workshops.shoppingcart.cart;

import java.util.UUID;

class BagValidator {

    static void checkId(String bagId) throws IllegalArgumentException {
        UUID.fromString(bagId);
    }
}
