package pl.workshops.shoppingcart.product.domain;

import pl.workshops.shoppingcart.Repository;

import java.util.List;

interface ProductRepository extends Repository<Product, String> {

    List<Product> findAll();
}
