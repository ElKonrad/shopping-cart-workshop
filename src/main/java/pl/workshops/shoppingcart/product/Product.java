package pl.workshops.shoppingcart.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Product {

    private String id;
    private String title;
    private BigDecimal unitPrice;
    private int quantity;
    private boolean available;
    private Date createdDate;

    public Product(String title, BigDecimal unitPrice, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.available = true;
        this.createdDate = new Date();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public boolean isNotAvailable() {
        return !available;
    }

    public void withdrawFromSale() {
        available = false;
    }

}
