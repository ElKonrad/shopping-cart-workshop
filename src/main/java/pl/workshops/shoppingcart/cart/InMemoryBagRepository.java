package pl.workshops.shoppingcart.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InMemoryBagRepository implements BagRepository {

    private Map<String, Bag> map = new HashMap<>();

    @Override
    public Bag save(Bag bag) {
        Objects.requireNonNull(bag);
        map.put(bag.getId(), bag);
        return bag;
    }

    @Override
    public Bag findById(String id) {
        return map.get(id);
    }

    @Override
    public void deleteById(String id) {
        map.remove(id);
    }

    @Override
    public List<Item> findAllItemsById(String bagId) {
        return map.get(bagId).getProducts();
    }
}
