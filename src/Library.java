import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Library {
    private static final int MAX_BOOKS_PER_MEMBER = 3;

    // Repositories (backed by Map)
    private final Repository<Book> books = new InMemoryRepository<>(Book::getBookId);
    private final Repository<Member> members = new InMemoryRepository<>(Member::getMemberId);

    // Set: ids of books currently borrowed (no duplicates, fast lookup)
    private final Set<Integer> borrowedBookIds = new HashSet<>();

    // Map: memberId -> List of books that member has borrowed
    private final Map<Integer, List<Book>> borrowedByMember = new HashMap<>();

    // ---------- Management ----------
    public void addBook(Book book) {
        books.save(book);
    }

    public void registerMember(Member member) {
        members.save(member);
        borrowedByMember.putIfAbsent(member.getMemberId(), new ArrayList<>());
    }

    public Repository<Book> getBooks() {
        return books;
    }

    public Repository<Member> getMembers() {
        return members;
    }

    // ---------- Borrow / return (callback via custom functional interface) ----------
    public void borrowBook(int memberId, int bookId, LibraryCallback callback) {
        Optional<Member> member = members.findById(memberId);
        Optional<Book> book = books.findById(bookId);

        if (member.isEmpty()) {
            callback.onResult(false, "Member " + memberId + " not found");
        } else if (book.isEmpty()) {
            callback.onResult(false, "Book " + bookId + " not found");
        } else if (borrowedBookIds.contains(bookId)) {
            callback.onResult(false, "'" + book.get().getTitle() + "' is already borrowed");
        } else if (borrowedByMember.get(memberId).size() >= MAX_BOOKS_PER_MEMBER) {
            callback.onResult(false, member.get().getName() + " reached the limit of " + MAX_BOOKS_PER_MEMBER + " books");
        } else {
            borrowedBookIds.add(bookId);
            borrowedByMember.get(memberId).add(book.get());
            callback.onResult(true, member.get().getName() + " borrowed '" + book.get().getTitle() + "'");
        }
    }

    public void returnBook(int memberId, int bookId, LibraryCallback callback) {
        List<Book> borrowed = borrowedByMember.get(memberId);
        boolean removed = borrowed != null && borrowed.removeIf(b -> b.getBookId() == bookId);
        if (removed) {
            borrowedBookIds.remove(bookId);
            callback.onResult(true, "Book " + bookId + " returned by member " + memberId);
        } else {
            callback.onResult(false, "Member " + memberId + " does not have book " + bookId);
        }
    }

    public List<Book> getBorrowedBooks(int memberId) {
        return new ArrayList<>(borrowedByMember.getOrDefault(memberId, List.of()));
    }

    // ---------- Stream API processing ----------

    // filtering
    public List<Book> availableBooks() {
        return books.findWhere(b -> !borrowedBookIds.contains(b.getBookId()));
    }

    public List<Book> booksByAuthor(String author) {
        return books.findWhere(b -> b.getAuthor().equalsIgnoreCase(author));
    }

    // filtering + sorting
    public List<Book> booksCheaperThan(double maxPrice) {
        return books.findAll().stream()
                .filter(b -> b.getPrice() < maxPrice)
                .sorted(Comparator.comparingDouble(Book::getPrice))
                .collect(Collectors.toList());
    }

    // mapping + sorting (method references)
    public List<String> sortedTitles() {
        return books.findAll().stream()
                .map(Book::getTitle)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> memberNamesSorted() {
        return members.findAll().stream()
                .map(Member::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    // grouping: member type -> count
    public Map<String, Long> memberCountByType() {
        return members.findAll().stream()
                .collect(Collectors.groupingBy(m -> m.getClass().getSimpleName(), Collectors.counting()));
    }

    // flatMap: all titles currently borrowed, per member name
    public Map<String, List<String>> borrowedTitlesByMember() {
        Map<String, List<String>> result = new HashMap<>();
        for (Member m : members.findAll()) {
            List<String> titles = borrowedByMember.get(m.getMemberId()).stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
            if (!titles.isEmpty()) {
                result.put(m.getName(), titles);
            }
        }
        return result;
    }

    // reduction
    public double totalValueOfBorrowedBooks() {
        return borrowedByMember.values().stream()
                .flatMap(List::stream)
                .mapToDouble(Book::getPrice)
                .sum();
    }

    // polymorphism + streams: each member type computes its own late fee
    public Map<String, Double> lateFeesForAllMembers(int daysLate) {
        return members.findAll().stream()
                .collect(Collectors.toMap(Member::getName, m -> m.calculateLateFees(daysLate)));
    }

    // generic map-style helper using the custom LibraryOperation interface
    public <R> List<R> mapMembers(LibraryOperation<? super Member, ? extends R> operation) {
        return RepositoryUtils.transform(members.findAll(), operation);
    }
}