package pl.workshops.shoppingcart.cart;

import org.junit.Before;
import org.junit.Test;
import pl.workshops.shoppingcart.Configuration;
import pl.workshops.shoppingcart.product.Product;
import pl.workshops.shoppingcart.product.ProductFacade;
import pl.workshops.shoppingcart.product.shoe.Brand;
import pl.workshops.shoppingcart.product.shoe.Shoe;
import pl.workshops.shoppingcart.product.shoe.Size;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartTest {

    private static final String CART_ID = UUID.randomUUID().toString();

    ProductFacade productFacade = new Configuration().productFacade();
    CartFacade cartFacade = new Configuration().cartFacade(productFacade);

    @Before
    public void init() {
        cartFacade.createCart(CART_ID);
    }

    @Test
    public void shouldNotBeEmptyCartWhenSomeProductIsAdded() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        productFacade.add(adidasShoe);
        Item item = new Item(adidasShoe.getId(), 1);

        //when
        cartFacade.addItem(CART_ID, item);

        //then
        assertTrue(cartFacade.showAllItems(CART_ID).size() > 0);
    }

    @Test
    public void shouldIncreaseNumberOfItemsInCartWhenAnotherProductIsAdded() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        Product nikeShoe = new Shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, Brand.NIKE, Size.B);
        productFacade.add(adidasShoe);
        productFacade.add(nikeShoe);

        cartFacade.addItem(CART_ID, new Item(adidasShoe.getId(), 1));

        //when
        cartFacade.addItem(CART_ID, new Item(nikeShoe.getId(), 1));

        //then
        assertEquals(2, cartFacade.showAllItems(CART_ID).size());
    }

    @Test
    public void shouldIncreaseTotalPriceWhenAnotherProductIsAdded() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        Product nikeShoe = new Shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, Brand.NIKE, Size.B);
        productFacade.add(adidasShoe);
        productFacade.add(nikeShoe);

        Item item = new Item(adidasShoe.getId(), 1);
        cartFacade.addItem(CART_ID, item);

        //when
        Cart cart = cartFacade.addItem(CART_ID, new Item(nikeShoe.getId(), 1));

        //then
        assertEquals(new BigDecimal(500), cart.calculateTotalCost());
    }

    @Test
    public void shouldDecreaseTotalPriceWhenSomeProductIsDeleted() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        Product nikeShoe = new Shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, Brand.NIKE, Size.B);
        productFacade.add(adidasShoe);
        productFacade.add(nikeShoe);

        Item adidasShoeItem = new Item(adidasShoe.getId(), 1);
        cartFacade.addItem(CART_ID, adidasShoeItem);
        Item nikeShoeItem = new Item(nikeShoe.getId(), 1);
        cartFacade.addItem(CART_ID, nikeShoeItem);

        //when
        Cart cart = cartFacade.removeItem(CART_ID, adidasShoeItem);

        //then
        assertEquals(new BigDecimal(300), cart.calculateTotalCost());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrownExceptionWhenTryingAddToCartTooManyProductsNotAvailableInStock() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        productFacade.add(adidasShoe);
        Item itemWithTooManyQuantity = new Item(adidasShoe.getId(), 20);

        //when
        cartFacade.addItem(CART_ID, itemWithTooManyQuantity);
    }

    @Test
    public void shouldClearCart() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        Product nikeShoe = new Shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, Brand.NIKE, Size.B);
        productFacade.add(adidasShoe);
        productFacade.add(nikeShoe);

        cartFacade.addItem(CART_ID, new Item(adidasShoe.getId(), 1));
        cartFacade.addItem(CART_ID, new Item(nikeShoe.getId(), 1));

        //when
        cartFacade.clearCart(CART_ID);

        //then
        assertEquals(0, cartFacade.showAllItems(CART_ID).size());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrownExceptionWhenTryingAddToCartProductWithdrawnFromSale() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        productFacade.add(adidasShoe);
        productFacade.withdrawFromSale(adidasShoe.getId());
        Item withdrawnFromSaleProduct = new Item(adidasShoe.getId(), 1);

        //when
        cartFacade.addItem(CART_ID, withdrawnFromSaleProduct);
    }
}