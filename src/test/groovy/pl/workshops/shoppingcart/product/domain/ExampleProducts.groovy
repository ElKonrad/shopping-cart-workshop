package pl.workshops.shoppingcart.product.domain


import pl.workshops.shoppingcart.product.dto.BrandTypeDto
import pl.workshops.shoppingcart.product.dto.ProductDto
import pl.workshops.shoppingcart.product.dto.SizeDto

trait ExampleProducts {
    ProductDto ADIDAS_SHOE_TO_ADD = ProductDto.shoe("ADIDAS GAZELLE", new BigDecimal(399), 10, BrandTypeDto.ADIDAS, SizeDto.A)
    ProductDto NIKE_SHOE_TO_ADD = ProductDto.shoe("NIKE FLYKNIT RACER", new BigDecimal(300), 10, BrandTypeDto.NIKE, SizeDto.B)

    static ProductDto shoeWithPrice(BigDecimal price) {
        ProductDto.shoe("ADIDAS GAZELLE", price, 10, BrandTypeDto.ADIDAS, SizeDto.A)
    }

    static ProductDto shoeWithQuantity(int quantity) {
        ProductDto.shoe("ADIDAS GAZELLE", new BigDecimal(399), quantity, BrandTypeDto.ADIDAS, SizeDto.A)
    }

    private static ProductDto shoeGroovyStyle(Map params) {
        ProductDto.builder()
                  .title(params.title ?: "Shoe sample title")
                  .unitPrice(params.unitPrice ?: new BigDecimal(400))
                  .quantity(params.quantity ?: 1)
                  .brand(params.brandTypeDto ?: BrandTypeDto.ADIDAS)
                  .size(params.sizeDto ?: SizeDto.A)
                  .build()
    }

    private static ProductDto shoeGroovyStyle2(Map params) {
        ProductDto dto = ProductDto.product(
                params.title ?: "Shoe sample title",
                params.unitPrice ?: new BigDecimal(400),
                params.quantity ?: 1
        )
        dto.@brand = params.brandTypeDto ?: BrandTypeDto.ADIDAS
        dto.@size = params.sizeDto ?: SizeDto.A
        dto
    }
}
