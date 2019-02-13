package pl.workshops.shoppingcart.product.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class InMemoryProductRepository implements ProductRepository {

    private Map<String, Product> map = new HashMap<>();

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
