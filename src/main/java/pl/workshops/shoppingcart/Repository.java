package pl.workshops.shoppingcart;

public interface Repository<T, ID> {

    T save(T object);

    T findById(ID id);

    void deleteById(ID id);

    default T findOneOrThrow(ID id) {
        T object = findById(id);
        if (object == null) {
            throw new ObjectNotFoundException("Object with ID = [" + id + "] not found");
        }
        return object;
    }
}
