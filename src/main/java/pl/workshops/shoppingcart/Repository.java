package pl.workshops.shoppingcart;

public interface Repository<T, ID> {

    T save(T bag);

    T findById(ID id);

    void deleteById(ID id);

    default T findOneOrThrow(ID id) {
        T bag = findById(id);
        if (bag == null) {
            throw new ObjectNotFoundException("Object with ID = [" + id + "] not found");
        }
        return bag;
    }
}
