package pl.workshops.shoppingcart.cart;

import pl.workshops.shoppingcart.product.Product;
import pl.workshops.shoppingcart.product.ProductFacade;

import java.util.List;

public class BagFacade {

    private final BagRepository bagRepository;
    private final ProductFacade productFacade;

    public BagFacade(BagRepository bagRepository, ProductFacade productFacade) {
        this.bagRepository = bagRepository;
        this.productFacade = productFacade;
    }

    public Bag createBag(String bagId) {
        return bagRepository.save(new Bag(bagId));
    }

    public Bag addItem(String bagId, Item item) {
        Bag bag = bagRepository.findById(bagId);
        Product foundProduct = productFacade.show(item.getProductId());
        item.setProduct(foundProduct);
        if (item.isNotEnoughQuantitiesInStock()) {
            throw new IllegalArgumentException("Requested quantities exceeds available stock");
        }
        bag.add(item);
        return bagRepository.save(bag);
    }

    public List<Item> showAllItems(String bagId) {
        BagValidator.checkId(bagId);
        return bagRepository.findAllItemsById(bagId);
    }

    public Bag removeItem(String bagId, Item item) {
        Bag bag = bagRepository.findById(bagId);
        bag.getProducts().remove(item);
        return bag;
    }

    public void clearBag(String bagId) {
        Bag bag = bagRepository.findById(bagId);
        bag.clear();
    }
}
