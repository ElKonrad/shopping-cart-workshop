package pl.workshops.shoppingcart.product.domain;

import pl.workshops.shoppingcart.product.dto.ProductDto;

import static java.util.Objects.nonNull;

class ProductCreator {

    Product from(ProductDto productDto) {
        if (nonNull(productDto.getBrand()) || nonNull(productDto.getSize()))
            return new Shoe(productDto.getTitle(),
                            productDto.getUnitPrice(),
                            productDto.getQuantity(),
                            Brand.valueOf(productDto.getBrand().name()),
                            Size.valueOf(productDto.getSize().name()));

        return new Product(productDto.getTitle(), productDto.getUnitPrice(), productDto.getQuantity());
    }
}