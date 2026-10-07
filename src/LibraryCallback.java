// Custom functional interface: callback invoked when an operation finishes
@FunctionalInterface
public interface LibraryCallback {
    void onResult(boolean success, String message);
}