import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Utility class showing upper (? extends) and lower (? super) bounded wildcards
public final class RepositoryUtils {
    private RepositoryUtils() {}

    // Upper bound: READ Books (or any subclass such as EBook) from the collection
    public static double totalPrice(Collection<? extends Book> books) {
        return books.stream().mapToDouble(Book::getPrice).sum();
    }

    // Upper bound with a type parameter
    public static <T extends Book> Optional<T> cheapest(Collection<T> books) {
        return books.stream().min(Comparator.comparingDouble(Book::getPrice));
    }

    // Lower bound: WRITE Books into a repository of Book (or a supertype of Book)
    public static void saveAll(Repository<? super Book> repo, Collection<? extends Book> books) {
        for (Book b : books) {
            repo.save(b);
        }
    }

    // PECS: Producer Extends, Consumer Super
    public static <T> void copyAll(Collection<? extends T> source, Collection<? super T> destination) {
        destination.addAll(source);
    }

    // Filtering with a custom functional interface
    public static <T> List<T> filter(Collection<? extends T> items, Filter<? super T> filter) {
        return items.stream()
                .filter(filter::matches)
                .collect(Collectors.toList());
    }

    // Mapping with a custom functional interface
    public static <T, R> List<R> transform(Collection<? extends T> items,
                                           LibraryOperation<? super T, ? extends R> operation) {
        return items.stream()
                .map(operation::apply)
                .collect(Collectors.toList());
    }

    // Unbounded wildcard: print anything
    public static void printAll(String title, Collection<?> items) {
        System.out.println(title);
        items.forEach(item -> System.out.println("  " + item));
    }
}