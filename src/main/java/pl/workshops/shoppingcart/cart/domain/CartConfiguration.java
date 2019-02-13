package pl.workshops.shoppingcart.cart.domain;

import pl.workshops.shoppingcart.product.domain.ProductFacade;

public class CartConfiguration {

    public CartFacade cartFacade(ProductFacade productFacade) {
        return new CartFacade(new InMemoryCartRepository(), productFacade);
    }
}
