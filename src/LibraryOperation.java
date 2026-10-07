// Custom functional interface: transforms an input into a result (used for mapping)
@FunctionalInterface
public interface LibraryOperation<T, R> {
    R apply(T input);
}