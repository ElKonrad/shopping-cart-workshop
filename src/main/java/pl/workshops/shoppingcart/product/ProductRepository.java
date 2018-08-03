package pl.workshops.shoppingcart.product;

import pl.workshops.shoppingcart.Repository;

import java.util.List;

public interface ProductRepository extends Repository<Product, String> {

    List<Product> findAll();
}
