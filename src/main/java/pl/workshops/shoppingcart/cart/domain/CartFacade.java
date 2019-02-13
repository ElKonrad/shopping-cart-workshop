package pl.workshops.shoppingcart.cart.domain;

import pl.workshops.shoppingcart.cart.dto.CartDto;
import pl.workshops.shoppingcart.cart.dto.ItemDto;
import pl.workshops.shoppingcart.product.domain.ProductFacade;
import pl.workshops.shoppingcart.product.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CartFacade {

    private final CartRepository cartRepository;
    private final ProductFacade productFacade;

    CartFacade(CartRepository cartRepository, ProductFacade productFacade) {
        this.cartRepository = cartRepository;
        this.productFacade = productFacade;
    }

    public CartDto createCart(String cartId) {
        return cartRepository.save(new Cart(cartId)).dto();
    }

    public CartDto addItem(String cartId, ItemDto itemDto) {
        Cart cart = cartRepository.findById(cartId);
        ProductDto foundProduct = productFacade.show(itemDto.getId());
        Item item = new Item(itemDto.getId(), itemDto.getQuantity(), foundProduct);
        cart.add(item);
        return cartRepository.save(cart).dto();
    }

    public List<ItemDto> showAllItems(String cartId) {
        CartValidator.checkId(cartId);
        return cartRepository.findAllItemsById(cartId)
                             .stream()
                             .map(Item::dto)
                             .collect(toList());
    }

    public void removeItem(String cartId, ItemDto item) {
        Cart cart = cartRepository.findById(cartId);
        cart.remove(item);
    }

    public void clearCart(String cartId) {
        Cart cart = cartRepository.findById(cartId);
        cart.clear();
    }

    public BigDecimal getTotalCost(String cartId) {
        Cart cart = cartRepository.findById(cartId);
        return cart.calculateTotalCost();
    }

}
