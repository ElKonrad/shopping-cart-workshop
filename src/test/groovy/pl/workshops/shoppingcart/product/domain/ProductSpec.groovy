package pl.workshops.shoppingcart.product.domain


import pl.workshops.shoppingcart.product.dto.ProductDto
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertEquals
import static pl.workshops.shoppingcart.product.domain.SampleProducts.ADIDAS_SHOE_TO_ADD

class ProductSpec extends Specification {

    private ProductFacade productFacade

    def setup() {
        productFacade = new ProductConfiguration().productFacade()
    }

    def "should add new product"() {
        given:
            ProductDto adidasShoe = ADIDAS_SHOE_TO_ADD

        when:
            ProductDto created = productFacade.create(adidasShoe)

        then:
            assertEquals(created.getId(), productFacade.show(created.getId()).getId())
    }
}
