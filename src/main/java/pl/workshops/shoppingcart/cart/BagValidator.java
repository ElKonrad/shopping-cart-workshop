package pl.workshops.shoppingcart.cart;

import java.util.UUID;

class BagValidator {

    static UUID checkId(String bagId) throws IllegalArgumentException {
        return UUID.fromString(bagId);
    }
}
