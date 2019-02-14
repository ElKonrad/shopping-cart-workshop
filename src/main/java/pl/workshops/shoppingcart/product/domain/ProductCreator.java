package pl.workshops.shoppingcart.product.domain;

import pl.workshops.shoppingcart.product.dto.ProductDto;

import static java.util.Objects.nonNull;

class ProductCreator {

    Product from(ProductDto productDto) {
        if (nonNull(productDto.getBrand()) || nonNull(productDto.getSize()))
            return Shoe.builder()
                       .title(productDto.getTitle())
                       .unitPrice(productDto.getUnitPrice())
                       .quantity(productDto.getQuantity())
                       .brand(Brand.valueOf(productDto.getBrand().name()))
                       .size(Size.valueOf(productDto.getSize().name()))
                       .build();

        return new Product(productDto.getTitle(), productDto.getUnitPrice(), productDto.getQuantity());
    }
}