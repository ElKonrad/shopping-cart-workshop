package pl.workshops.shoppingcart.cart;

import pl.workshops.shoppingcart.Repository;

import java.util.List;

public interface BagRepository extends Repository<Bag, String> {

    List<Item> findAllItemsById(String bagId);
}
