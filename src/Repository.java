import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Generic repository interface
public interface Repository<T> {
    void save(T item);

    Optional<T> findById(int id);

    List<T> findAll();

    boolean deleteById(int id);

    int count();

    // Lower-bounded wildcard: a Filter<Object> or Filter<Book> both work for a Repository<Book>
    default List<T> findWhere(Filter<? super T> filter) {
        return findAll().stream()
                .filter(filter::matches)
                .collect(Collectors.toList());
    }
}