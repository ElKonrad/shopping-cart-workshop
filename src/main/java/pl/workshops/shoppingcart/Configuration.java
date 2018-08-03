package pl.workshops.shoppingcart;

import pl.workshops.shoppingcart.cart.BagFacade;
import pl.workshops.shoppingcart.cart.InMemoryBagRepository;
import pl.workshops.shoppingcart.product.InMemoryProductRepository;
import pl.workshops.shoppingcart.product.ProductFacade;

public class Configuration {

    public BagFacade bagFacade() {
        return new BagFacade(new InMemoryBagRepository(), productFacade());
    }

    public ProductFacade productFacade() {
        return new ProductFacade(new InMemoryProductRepository());
    }

    public BagFacade bagFacade(ProductFacade productFacade) {
        return new BagFacade(new InMemoryBagRepository(), productFacade);
    }

}
