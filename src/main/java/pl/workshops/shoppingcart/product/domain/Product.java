package pl.workshops.shoppingcart.product.domain;

import lombok.Getter;
import pl.workshops.shoppingcart.product.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

class Product {

    private static final boolean AVAILABLE = true;
    private static final boolean UNAVAILABLE = false;

    @Getter
    private String id;
    @Getter
    private String title;
    @Getter
    private BigDecimal unitPrice;
    @Getter
    private int quantity;
    @Getter
    private boolean available;
    private Date createdDate;

    Product(String title, BigDecimal unitPrice, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.available = AVAILABLE;
        this.createdDate = new Date();
    }

    void withdrawFromSale() {
        available = UNAVAILABLE;
    }

    boolean canBeOrder() {
        return available && quantity != 0;
    }

    public ProductDto dto() {
        return ProductDto.builder()
                         .id(id)
                         .title(title)
                         .unitPrice(unitPrice)
                         .quantity(quantity)
                         .canBeOrder(canBeOrder())
                         .build();
    }
}
