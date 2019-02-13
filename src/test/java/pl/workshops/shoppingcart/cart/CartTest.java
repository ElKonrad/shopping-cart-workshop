package pl.workshops.shoppingcart.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.workshops.shoppingcart.cart.domain.CartConfiguration;
import pl.workshops.shoppingcart.cart.domain.CartFacade;
import pl.workshops.shoppingcart.cart.dto.CartDto;
import pl.workshops.shoppingcart.cart.dto.ItemDto;
import pl.workshops.shoppingcart.product.SampleProducts;
import pl.workshops.shoppingcart.product.domain.ProductConfiguration;
import pl.workshops.shoppingcart.product.domain.ProductFacade;
import pl.workshops.shoppingcart.product.dto.ProductDto;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.workshops.shoppingcart.product.SampleProducts.ADIDAS_SHOE_TO_ADD;
import static pl.workshops.shoppingcart.product.SampleProducts.NIKE_SHOE_TO_ADD;
import static pl.workshops.shoppingcart.product.SampleProducts.productWithQuantity;

class CartTest {

    private static final String CART_ID = UUID.randomUUID().toString();
    private static final int ONE_ITEM_IN_CART = 1;
    private static final int TWO_ITEMS_IN_CART = 2;

    private ProductFacade productFacade;
    private CartFacade cartFacade;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
        cartFacade = new CartConfiguration().cartFacade(productFacade);
        cartFacade.createCart(CART_ID);
    }

    @Test
    void shouldNotBeEmptyCartWhenSomeProductIsAdded() {
        //given
        ProductDto created = productFacade.create(ADIDAS_SHOE_TO_ADD);
        ItemDto itemToAdd = new ItemDto(created.getId(), 1);

        //when
        cartFacade.addItem(CART_ID, itemToAdd);

        //then
        assertEquals(1, cartFacade.showAllItems(CART_ID).size());
    }

    @Test
    void shouldIncreaseNumberOfItemsInCartWhenAnotherProductIsAdded() {
        //given
        ProductDto createdAdidasShoe = productFacade.create(ADIDAS_SHOE_TO_ADD);
        ProductDto createdNikeShoe = productFacade.create(NIKE_SHOE_TO_ADD);

        //when
        cartFacade.addItem(CART_ID, new ItemDto(createdAdidasShoe.getId(), 1));

        //then
        assertEquals(ONE_ITEM_IN_CART, cartFacade.showAllItems(CART_ID).size());

        //when
        cartFacade.addItem(CART_ID, new ItemDto(createdNikeShoe.getId(), 1));

        //then
        assertEquals(TWO_ITEMS_IN_CART, cartFacade.showAllItems(CART_ID).size());
    }

    @Test
    void shouldIncreaseTotalPriceWhenAnotherProductIsAdded() {
        //given
        ProductDto created1 = productFacade.create(SampleProducts.productWithPrice(new BigDecimal(200)));
        ProductDto created2 = productFacade.create(SampleProducts.productWithPrice(new BigDecimal(300)));
        ItemDto item1 = new ItemDto(created1.getId(), 1);
        ItemDto item2 = new ItemDto(created2.getId(), 1);

        //and
        cartFacade.addItem(CART_ID, item1);

        //when
        CartDto cart = cartFacade.addItem(CART_ID, item2);

        //then
        assertEquals(new BigDecimal(500), cartFacade.getTotalCost(cart.getId()));
    }

    @Test
    void shouldDecreaseTotalPriceWhenSomeProductIsDeleted() {
        //given
        ProductDto created1 = productFacade.create(SampleProducts.productWithPrice(new BigDecimal(200)));
        ProductDto created2 = productFacade.create(SampleProducts.productWithPrice(new BigDecimal(300)));
        ItemDto item1 = new ItemDto(created1.getId(), 1);
        ItemDto item2 = new ItemDto(created2.getId(), 1);

        //and
        cartFacade.addItem(CART_ID, item1);
        cartFacade.addItem(CART_ID, item2);

        //when
        cartFacade.removeItem(CART_ID, item1);

        //then
        assertEquals(new BigDecimal(300), cartFacade.getTotalCost(CART_ID));
    }

    @Test
    void shouldThrownExceptionWhenTryingAddToCartTooManyProductsNotAvailableInStock() {
        //given
        ProductDto created = productFacade.create(productWithQuantity(10));
        ItemDto itemWithTooManyQuantity = new ItemDto(created.getId(), 20);

        //expect
        assertThrows(IllegalStateException.class, () -> cartFacade.addItem(CART_ID, itemWithTooManyQuantity));
    }

    @Test
    void shouldClearCart() {
        //given
        ProductDto created1 = productFacade.create(ADIDAS_SHOE_TO_ADD);
        ProductDto created2 = productFacade.create(NIKE_SHOE_TO_ADD);

        //and
        cartFacade.addItem(CART_ID, new ItemDto(created1.getId(), 1));
        cartFacade.addItem(CART_ID, new ItemDto(created2.getId(), 1));

        //when
        cartFacade.clearCart(CART_ID);

        //then
        assertEquals(0, cartFacade.showAllItems(CART_ID).size());
    }

    @Test
    void shouldThrownExceptionWhenTryingAddToCartProductWithdrawnFromSale() {
        //given
        ProductDto created = productFacade.create(ADIDAS_SHOE_TO_ADD);

        //and
        productFacade.withdrawFromSale(created.getId());
        ItemDto withdrawnFromSaleProduct = new ItemDto(created.getId(), 1);

        //expect
        assertThrows(IllegalStateException.class, () -> cartFacade.addItem(CART_ID, withdrawnFromSaleProduct));
    }
}