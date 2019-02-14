package pl.workshops.shoppingcart.product.domain;

import lombok.Builder;
import pl.workshops.shoppingcart.product.dto.ProductDto;

import java.math.BigDecimal;

class Shoe extends Product {

    private static final String SHOE_PREFIX_TITLE = "#SHOE# ";
    private Brand brand;
    private Size size;

    @Builder
    Shoe(String title,
                BigDecimal unitPrice,
                int quantity,
                Brand brand,
                Size size) {
        super(title, unitPrice, quantity);
        this.brand = brand;
        this.size = size;
    }

    public String getTitle() {
        return SHOE_PREFIX_TITLE + super.getTitle();
    }

    @Override
    public ProductDto dto() {
        return ProductDto
                .builder()
                .id(getId())
                .title(getTitle())
                .unitPrice(getUnitPrice())
                .quantity(getQuantity())
                .canBeOrder(isAvailable())
                .brand(brand.dto())
                .size(size.dto())
                .build();
    }
}
