package pl.workshops.shoppingcart.product.domain;

import pl.workshops.shoppingcart.product.dto.BrandTypeDto;

enum Brand {
    ADIDAS, NIKE, NEW_BALANCE;

    BrandTypeDto dto() {
        return BrandTypeDto.valueOf(name());
    }
}
