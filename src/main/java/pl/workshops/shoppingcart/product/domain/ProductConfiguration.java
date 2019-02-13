package pl.workshops.shoppingcart.product.domain;

public class ProductConfiguration {

    public ProductFacade productFacade() {
        return new ProductFacade(new InMemoryProductRepository(), new ProductCreator());
    }
}
