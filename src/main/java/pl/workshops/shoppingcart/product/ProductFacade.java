package pl.workshops.shoppingcart.product;

import java.util.List;
import java.util.Objects;

public class ProductFacade {

    private final ProductRepository productRepository;

    public ProductFacade(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product add(Product product) {
        Objects.requireNonNull(product);
        return productRepository.save(product);
    }

    public Product show(String id) {
        return productRepository.findOneOrThrow(id);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void withdrawFromSale(String id) {
        Product product = productRepository.findOneOrThrow(id);
        product.withdrawFromSale();
    }
}
