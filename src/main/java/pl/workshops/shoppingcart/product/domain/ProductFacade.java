package pl.workshops.shoppingcart.product.domain;

import pl.workshops.shoppingcart.product.dto.ProductDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductFacade {

    private final ProductRepository productRepository;
    private final ProductCreator productCreator;

    public ProductFacade(ProductRepository productRepository, ProductCreator productCreator) {
        this.productRepository = productRepository;
        this.productCreator = productCreator;
    }

    public ProductDto create(ProductDto productDto) {
        Objects.requireNonNull(productDto);
        Product created = productRepository.save(productCreator.from(productDto));
        return created.dto();
    }

    public ProductDto show(String id) {
        Product product = productRepository.findOneOrThrow(id);
        return product.dto();
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> showAll() {
        return productRepository.findAll()
                                .stream()
                                .map(Product::dto)
                                .collect(Collectors.toList());
    }

    public ProductDto withdrawFromSale(String id) {
        Product product = productRepository.findOneOrThrow(id);
        product.withdrawFromSale();
        return productRepository.save(product).dto();
    }
}
