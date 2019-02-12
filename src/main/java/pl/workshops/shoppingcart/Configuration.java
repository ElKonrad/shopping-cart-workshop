package pl.workshops.shoppingcart;

import pl.workshops.shoppingcart.cart.CartFacade;
import pl.workshops.shoppingcart.cart.InMemoryCartRepository;
import pl.workshops.shoppingcart.product.InMemoryProductRepository;
import pl.workshops.shoppingcart.product.ProductFacade;

public class Configuration {

    public CartFacade cartFacade() {
        return new CartFacade(new InMemoryCartRepository(), productFacade());
    }

    public ProductFacade productFacade() {
        return new ProductFacade(new InMemoryProductRepository());
    }

    public CartFacade cartFacade(ProductFacade productFacade) {
        return new CartFacade(new InMemoryCartRepository(), productFacade);
    }

}
