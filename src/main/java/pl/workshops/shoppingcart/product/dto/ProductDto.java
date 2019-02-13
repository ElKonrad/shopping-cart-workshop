package pl.workshops.shoppingcart.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ProductDto {

    private String id;
    private String title;
    private BigDecimal unitPrice;
    private int quantity;
    private boolean canBeOrder;
    private BrandTypeDto brand;
    private SizeDto size;

    public static ProductDto product(String title, BigDecimal unitPrice, int quantity) {
        return ProductDto.builder()
                         .title(title)
                         .unitPrice(unitPrice)
                         .quantity(quantity)
                         .build();
    }

    public static ProductDto shoe(String title, BigDecimal unitPrice, int quantity, BrandTypeDto brandTypeDto, SizeDto sizeDto) {
        return ProductDto.builder()
                         .title(title)
                         .unitPrice(unitPrice)
                         .quantity(quantity)
                         .brand(brandTypeDto)
                         .size(sizeDto)
                         .build();
    }

    public boolean cannotBeOrder() {
        return !canBeOrder;
    }
}
