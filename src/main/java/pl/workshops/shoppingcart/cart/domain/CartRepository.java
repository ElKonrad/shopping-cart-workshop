package pl.workshops.shoppingcart.cart.domain;

import pl.workshops.shoppingcart.Repository;

import java.util.List;

interface CartRepository extends Repository<Cart, String> {

    List<Item> findAllItemsById(String cartId);
}
