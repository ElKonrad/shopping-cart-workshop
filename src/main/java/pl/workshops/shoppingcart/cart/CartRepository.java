package pl.workshops.shoppingcart.cart;

import pl.workshops.shoppingcart.Repository;

import java.util.List;

public interface CartRepository extends Repository<Cart, String> {

    List<Item> findAllItemsById(String cartId);
}
