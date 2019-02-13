package pl.workshops.shoppingcart.product.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.workshops.shoppingcart.product.dto.ProductDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static pl.workshops.shoppingcart.product.domain.SampleProducts.ADIDAS_SHOE_TO_ADD;

class ProductTest {

    private ProductFacade productFacade;

    @BeforeEach
    void setup() {
        productFacade = new ProductConfiguration().productFacade();
    }

    @Test
    void shouldAddNewProduct() {
        //given
        ProductDto adidasShoe = ADIDAS_SHOE_TO_ADD;

        //when
        ProductDto created = productFacade.create(adidasShoe);

        //then
        assertEquals(created.getId(), productFacade.show(created.getId()).getId());
    }

    @Test
    void shouldDeleteExistingProduct() {
        //given
        ProductDto created = productFacade.create(ADIDAS_SHOE_TO_ADD);

        //when
        productFacade.delete(created.getId());

        //then
        assertEquals(0, productFacade.findAll().size());
    }

    @Test
    void shouldWithdrawProductFromSale() {
        //given
        ProductDto created = productFacade.create(ADIDAS_SHOE_TO_ADD);

        //when
        productFacade.withdrawFromSale(created.getId());

        //then
        assertFalse(ADIDAS_SHOE_TO_ADD.isCanBeOrder());
    }
}