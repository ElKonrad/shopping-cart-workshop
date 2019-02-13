package pl.workshops.shoppingcart.cart.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class InMemoryCartRepository implements CartRepository {

    private Map<String, Cart> map = new HashMap<>();

    @Override
    public Cart save(Cart cart) {
        Objects.requireNonNull(cart);
        map.put(cart.getId(), cart);
        return cart;
    }

    @Override
    public Cart findById(String id) {
        return map.get(id);
    }

    @Override
    public void deleteById(String id) {
        map.remove(id);
    }

    @Override
    public List<Item> findAllItemsById(String cartId) {
        return map.get(cartId).getItems();
    }
}
