package pl.workshops.shoppingcart.cart.domain

import pl.workshops.shoppingcart.cart.dto.CartDto
import pl.workshops.shoppingcart.cart.dto.ItemDto
import pl.workshops.shoppingcart.product.domain.ExampleProducts
import pl.workshops.shoppingcart.product.domain.ProductConfiguration
import pl.workshops.shoppingcart.product.domain.ProductFacade
import pl.workshops.shoppingcart.product.dto.ProductDto
import spock.lang.Specification
import spock.lang.Unroll

class CartSpec extends Specification implements ExampleProducts {

    private static final String CART_ID = UUID.randomUUID().toString()
    private static final int ONE_ITEM_IN_CART = 1
    private static final int TWO_ITEMS_IN_CART = 2

    private ProductFacade productFacade
    private CartFacade cartFacade

    def setup() {
        productFacade = new ProductConfiguration().productFacade()
        cartFacade = new CartConfiguration().cartFacade(productFacade)
        cartFacade.createCart(CART_ID)
    }

    @Unroll
    def "should not be empty cart when some product is added"() {
        given:
            ProductDto created = productFacade.create(sampleProduct)
            ItemDto itemToAdd = new ItemDto(created.getId(), 1)

        when:
            cartFacade.addItem(CART_ID, itemToAdd)

        then:
            cartFacade.showAllItems(CART_ID).size() == 1

        where:
            sampleProduct      | _
            ADIDAS_SHOE_TO_ADD | _
            NIKE_SHOE_TO_ADD   | _
    }

    def "should increase number of items in cart when another product is added"() {
        given:
            ProductDto createdAdidasShoe = productFacade.create(ADIDAS_SHOE_TO_ADD)
            ProductDto createdNikeShoe = productFacade.create(NIKE_SHOE_TO_ADD)

        when:
            cartFacade.addItem(CART_ID, new ItemDto(createdAdidasShoe.getId(), 1))

        then:
            cartFacade.showAllItems(CART_ID).size() == ONE_ITEM_IN_CART

        when:
            cartFacade.addItem(CART_ID, new ItemDto(createdNikeShoe.getId(), 1))

        then:
            cartFacade.showAllItems(CART_ID).size() == TWO_ITEMS_IN_CART
    }

    @Unroll
    def "when item with price #firstItemTotalPrice is in cart and another item is added with price #secondItemTotalPrice then total price of cart should be #totalCost"() {
        given:
            ProductDto created1 = productFacade.create(productWithPrice(new BigDecimal(firstProductPrice)))
            ProductDto created2 = productFacade.create(productWithPrice(new BigDecimal(secondProductPrice)))
            ItemDto item1 = new ItemDto(created1.getId(), firstProductQuantity)
            ItemDto item2 = new ItemDto(created2.getId(), secondProductQuantity)

        and:
            cartFacade.addItem(CART_ID, item1)

        when:
            CartDto cart = cartFacade.addItem(CART_ID, item2)

        then:
            cartFacade.getTotalCost(cart.getId()) == new BigDecimal(totalCost)

        where:
            firstProductPrice | firstProductQuantity | secondProductPrice | secondProductQuantity || totalCost
            100               | 1                    | 100                | 1                     || 200
            100               | 2                    | 100                | 1                     || 300
            100               | 2                    | 100                | 2                     || 400

            firstItemTotalPrice = firstProductPrice * firstProductQuantity
            secondItemTotalPrice = secondProductPrice * secondProductQuantity
    }

    @Unroll
    def "should increase total price when another product is added"() {
        given:
            ProductDto created1 = productFacade.create(productWithPrice(new BigDecimal(firstProductPrice)))
            ProductDto created2 = productFacade.create(productWithPrice(new BigDecimal(secondProductPrice)))
            ItemDto item1 = new ItemDto(created1.getId(), firstProductQuantity)
            ItemDto item2 = new ItemDto(created2.getId(), secondProductQuantity)

        and:
            cartFacade.addItem(CART_ID, item1)

        when:
            CartDto cart = cartFacade.addItem(CART_ID, item2)

        then:
            cartFacade.getTotalCost(cart.getId()) == new BigDecimal(totalCost)

        where:
            [firstProductPrice, firstProductQuantity] << [[100, 1], [100, 2], [100, 2]]
            [secondProductPrice, secondProductQuantity] << [[100, 1], [100, 1], [100, 2]]
            totalCost << [200, 300, 400]
    }

    def "should decrease total price when some product is deleted"() {
        given:
            ProductDto created1 = productFacade.create(productWithPrice(new BigDecimal(200)))
            ProductDto created2 = productFacade.create(productWithPrice(new BigDecimal(300)))
            ItemDto item1 = new ItemDto(created1.getId(), 1)
            ItemDto item2 = new ItemDto(created2.getId(), 1)

        and:
            cartFacade.addItem(CART_ID, item1)
            cartFacade.addItem(CART_ID, item2)

        when:
            cartFacade.removeItem(CART_ID, item1)

        then:
            cartFacade.getTotalCost(CART_ID) == new BigDecimal(300)
    }

    def "should thrown an exception when trying add to cart too many products not available in stock"() {
        given:
            ProductDto created = productFacade.create(productWithQuantity(10))
            ItemDto itemWithTooManyQuantity = new ItemDto(created.getId(), 20)

        when:
            cartFacade.addItem(CART_ID, itemWithTooManyQuantity)

        then:
            thrown(IllegalStateException.class)
    }

    def "should clear cart"() {
        given:
            ProductDto created1 = productFacade.create(ADIDAS_SHOE_TO_ADD)
            ProductDto created2 = productFacade.create(NIKE_SHOE_TO_ADD)

        and:
            cartFacade.addItem(CART_ID, new ItemDto(created1.getId(), 1))
            cartFacade.addItem(CART_ID, new ItemDto(created2.getId(), 1))

        when:
            cartFacade.clearCart(CART_ID)

        then:
            cartFacade.showAllItems(CART_ID).size() == 0
    }

    def "should thrown an exception when trying add to cart product withdrawn from sale"() {
        given:
            ProductDto created = productFacade.create(ADIDAS_SHOE_TO_ADD)

        and:
            productFacade.withdrawFromSale(created.getId())
            ItemDto withdrawnFromSaleProduct = new ItemDto(created.getId(), 1)

        when:
            cartFacade.addItem(CART_ID, withdrawnFromSaleProduct)

        then:
            thrown(IllegalStateException.class)
    }
}
