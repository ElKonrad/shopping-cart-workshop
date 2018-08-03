package pl.workshops.shoppingcart.cart;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBagRepository implements BagRepository {

    private ConcurrentHashMap<String, Bag> map = new ConcurrentHashMap<>();

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
