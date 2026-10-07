// Custom functional interface: decides whether an item should be kept
@FunctionalInterface
public interface Filter<T> {
    boolean matches(T item);

    default Filter<T> and(Filter<? super T> other) {
        return item -> this.matches(item) && other.matches(item);
    }

    default Filter<T> negate() {
        return item -> !this.matches(item);
    }
}

