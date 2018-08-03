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

public class BagTest {

    private static final String BAG_ID = UUID.randomUUID().toString();

    ProductFacade productFacade = new Configuration().productFacade();
    BagFacade bagFacade = new Configuration().bagFacade(productFacade);

    @Before
    public void init() {
        bagFacade.createBag(BAG_ID);
    }

    @Test
    public void shouldNotBeEmptyBagWhenSomeProductIsAdded() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        productFacade.add(adidasShoe);
        Item item = new Item(adidasShoe.getId(), 1);

        //when
        bagFacade.addItem(BAG_ID, item);

        //then
        assertTrue(bagFacade.showAllItems(BAG_ID).size() > 0);
    }

    @Test
    public void shouldIncreaseNumberOfItemsInBagWhenAnotherProductIsAdded() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        Product nikeShoe = new Shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, Brand.NIKE, Size.B);
        productFacade.add(adidasShoe);
        productFacade.add(nikeShoe);

        bagFacade.addItem(BAG_ID, new Item(adidasShoe.getId(), 1));

        //when
        bagFacade.addItem(BAG_ID, new Item(nikeShoe.getId(), 1));

        //then
        assertEquals(2, bagFacade.showAllItems(BAG_ID).size());
    }

    @Test
    public void shouldIncreaseTotalPriceWhenAnotherProductIsAdded() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        Product nikeShoe = new Shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, Brand.NIKE, Size.B);
        productFacade.add(adidasShoe);
        productFacade.add(nikeShoe);

        Item item = new Item(adidasShoe.getId(), 1);
        bagFacade.addItem(BAG_ID, item);

        //when
        Bag bag = bagFacade.addItem(BAG_ID, new Item(nikeShoe.getId(), 1));

        //then
        assertEquals(new BigDecimal(500), bag.calculateTotalCost());
    }

    @Test
    public void shouldDecreaseTotalPriceWhenSomeProductIsDeleted() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        Product nikeShoe = new Shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, Brand.NIKE, Size.B);
        productFacade.add(adidasShoe);
        productFacade.add(nikeShoe);

        Item adidasShoeItem = new Item(adidasShoe.getId(), 1);
        bagFacade.addItem(BAG_ID, adidasShoeItem);
        Item nikeShoeItem = new Item(nikeShoe.getId(), 1);
        bagFacade.addItem(BAG_ID, nikeShoeItem);

        //when
        Bag bag = bagFacade.removeItem(BAG_ID, adidasShoeItem);

        //then
        assertEquals(new BigDecimal(300), bag.calculateTotalCost());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrownExceptionWhenTryingAddToBagTooManyQuantityProductThatIsNotInStock() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        productFacade.add(adidasShoe);
        Item itemWithTooManyQuantity = new Item(adidasShoe.getId(), 20);

        //when
        bagFacade.addItem(BAG_ID, itemWithTooManyQuantity);
    }

    @Test
    public void shouldClearBag() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        Product nikeShoe = new Shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, Brand.NIKE, Size.B);
        productFacade.add(adidasShoe);
        productFacade.add(nikeShoe);

        bagFacade.addItem(BAG_ID, new Item(adidasShoe.getId(), 1));
        bagFacade.addItem(BAG_ID, new Item(nikeShoe.getId(), 1));

        //when
        bagFacade.clearBag(BAG_ID);

        //then
        assertEquals(0, bagFacade.showAllItems(BAG_ID).size());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrownExceptionWhenTryingAddToBagProductWithdrawnFromSale() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(200), 10, Brand.ADIDAS, Size.A);
        productFacade.add(adidasShoe);
        productFacade.withdrawFromSale(adidasShoe.getId());
        Item withdrawnFromSaleProduct = new Item(adidasShoe.getId(), 1);

        //when
        bagFacade.addItem(BAG_ID, withdrawnFromSaleProduct);
    }
}