package pl.workshops.shoppingcart.product.domain

import pl.workshops.shoppingcart.product.dto.ProductDto
import spock.lang.Specification

import java.lang.Void as Should

class ProductSpec extends Specification implements ExampleProducts {

    private ProductFacade productFacade

    def setup() {
        productFacade = new ProductConfiguration().productFacade()
    }

    def "should get a product"() {
        when: "we create a product"
            ProductDto created = productFacade.create(ADIDAS_SHOE_TO_ADD)

        then: "system has this product"
            with(productFacade.show(created.id)) {
                id == created.id
                title == created.title
                unitPrice == created.unitPrice
                quantity == created.quantity
                brand == created.brand
                size == created.size
            }
    }

    Should "delete existing product"() {
        given:
            ProductDto created = productFacade.create(ADIDAS_SHOE_TO_ADD)

        when:
            productFacade.delete(created.id)

        then:
            productFacade.showAll().size() == 0
    }

    Should "withdraw product from sale"() {
        given:
            ProductDto created = productFacade.create(ADIDAS_SHOE_TO_ADD)

        when:
            ProductDto withdrawn = productFacade.withdrawFromSale(created.id)

        then:
            withdrawn.isCanBeOrder() == false
    }
}
