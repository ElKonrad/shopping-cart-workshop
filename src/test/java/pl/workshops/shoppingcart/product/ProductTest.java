package pl.workshops.shoppingcart.product;

import org.junit.Test;
import pl.workshops.shoppingcart.Configuration;
import pl.workshops.shoppingcart.product.shoe.Brand;
import pl.workshops.shoppingcart.product.shoe.Shoe;
import pl.workshops.shoppingcart.product.shoe.Size;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductTest {

    ProductFacade productFacade = new Configuration().productFacade();

    @Test
    public void shouldAddNewProduct() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(399), 10, Brand.ADIDAS, Size.A);

        //when
        productFacade.add(adidasShoe);

        //then
        assertEquals(adidasShoe, productFacade.show(adidasShoe.getId()));
    }

    @Test
    public void shouldDeleteExistingProduct() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(399), 10, Brand.ADIDAS, Size.A);
        productFacade.add(adidasShoe);

        //when
        productFacade.delete(adidasShoe.getId());

        //then
        assertEquals(0, productFacade.findAll().size());
    }

    @Test
    public void shouldWithdrawProductFromSale() {
        //given
        Product adidasShoe = new Shoe("ADIDAS GAZELLE", new BigDecimal(399), 10, Brand.ADIDAS, Size.A);
        productFacade.add(adidasShoe);

        //when
        productFacade.withdrawFromSale(adidasShoe.getId());

        //then
        assertTrue(adidasShoe.isNotAvailable());
    }
}