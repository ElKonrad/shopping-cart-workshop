package pl.workshops.shoppingcart.product.domain;

import pl.workshops.shoppingcart.product.dto.SizeDto;

enum Size {
    A(40), B(41), C(42), D(43), E(44), F(45);

    private int size;

    Size(int size) {
        this.size = size;
    }

    public int getNumber() {
        return size;
    }

    SizeDto dto() {
        return SizeDto.valueOf(name());
    }
}
