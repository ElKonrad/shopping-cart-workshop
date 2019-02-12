package pl.workshops.shoppingcart.cart;

import pl.workshops.shoppingcart.product.Product;
import pl.workshops.shoppingcart.product.ProductFacade;

import java.util.List;

public class CartFacade {

    private final CartRepository cartRepository;
    private final ProductFacade productFacade;

    public CartFacade(CartRepository cartRepository, ProductFacade productFacade) {
        this.cartRepository = cartRepository;
        this.productFacade = productFacade;
    }

    public Cart createCart(String cartId) {
        return cartRepository.save(new Cart(cartId));
    }

    public Cart addItem(String cartId, Item item) {
        Cart cart = cartRepository.findById(cartId);
        Product foundProduct = productFacade.show(item.getProductId());
        item.setProduct(foundProduct);
        if (item.isNotEnoughQuantitiesInStock()) {
            throw new IllegalArgumentException("Requested quantities exceeds available stock");
        }
        cart.add(item);
        return cartRepository.save(cart);
    }

    public List<Item> showAllItems(String cartId) {
        CartValidator.checkId(cartId);
        return cartRepository.findAllItemsById(cartId);
    }

    public Cart removeItem(String cartId, Item item) {
        Cart cart = cartRepository.findById(cartId);
        cart.getProducts().remove(item);
        return cart;
    }

    public void clearCart(String cartId) {
        Cart cart = cartRepository.findById(cartId);
        cart.clear();
    }
}
