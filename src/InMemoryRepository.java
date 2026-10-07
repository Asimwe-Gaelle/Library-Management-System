import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

// Map-backed repository. The id extractor is passed as a method reference (e.g. Book::getBookId)
public class InMemoryRepository<T> implements Repository<T> {
    private final Map<Integer, T> store = new LinkedHashMap<>();
    private final Function<T, Integer> idExtractor;

    public InMemoryRepository(Function<T, Integer> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public void save(T item) {
        store.put(idExtractor.apply(item), item);
    }

    @Override
    public Optional<T> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(int id) {
        return store.remove(id) != null;
    }

    @Override
    public int count() {
        return store.size();
    }
}