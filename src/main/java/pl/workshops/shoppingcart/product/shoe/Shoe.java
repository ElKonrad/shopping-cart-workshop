package pl.workshops.shoppingcart.product.shoe;

import pl.workshops.shoppingcart.product.Product;

import java.math.BigDecimal;

public class Shoe extends Product {

    private Brand brand;
    private Size size;

    public Shoe(String title, BigDecimal unitPrice, int quantity, Brand brand, Size size) {
        super(title, unitPrice, quantity);
        this.brand = brand;
        this.size = size;
    }
}
