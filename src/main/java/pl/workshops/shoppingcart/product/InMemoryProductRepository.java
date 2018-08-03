package pl.workshops.shoppingcart.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    private ConcurrentHashMap<String, Product> map = new ConcurrentHashMap<>();

    @Override
    public Product save(Product product) {
        Objects.requireNonNull(product);
        map.put(product.getId(), product);
        return product;
    }

    @Override
    public Product findById(String id) {
        return map.get(id);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void deleteById(String id) {
        map.remove(id);
    }
}
