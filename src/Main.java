import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ---------- Original data ----------
        Book book = new Book(101, "To Kill a MockingBird", "Harper Lee", 8500);
        StudentMember student = new StudentMember(1, "John Doe", "johndoe@gmail.com", "ST001", "Economics");
        StaffMember staff = new StaffMember(5, "Byiringiro Eric", "ericbyiringiro@gmail.com", "DEP001", "Information Technology");

        System.out.println("Book Information:");
        System.out.println(book);
        System.out.println("\nMember Information:");
        System.out.println(student);
        System.out.println(staff);
        System.out.println("Student Late Fees: " + student.calculateLateFees(3));
        System.out.println("Staff Late Fees: " + staff.calculateLateFees(2));

        // ---------- Lab 1: collections ----------
        System.out.println("\n===== Lab 1: Collections =====");
        Library library = new Library();
        library.addBook(book);
        library.addBook(new Book(102, "Go Set a Watchman", "Harper Lee", 7000));
        library.addBook(new Book(103, "Clean Code", "Robert Martin", 25000));
        library.addBook(new EBook(104, "Effective Java", "Joshua Bloch", 18000, 12.5));
        library.addBook(new Book(105, "Animal Farm", "George Orwell", 5000));

        library.registerMember(student);
        library.registerMember(staff);
        library.registerMember(new PublisherMember(7, "Alice Uwase", "alice@press.rw", "Kigali Press"));
        library.registerMember(new LibrarianMember(9, "Grace Mutoni", "grace@library.rw", "Grace M."));

        LibraryCallback printer = (ok, msg) -> System.out.println((ok ? "[OK]   " : "[FAIL] ") + msg);

        library.borrowBook(1, 101, printer);
        library.borrowBook(1, 103, printer);
        library.borrowBook(5, 101, printer);   // already borrowed
        library.borrowBook(5, 104, printer);
        library.borrowBook(99, 102, printer);  // unknown member
        library.borrowBook(1, 102, printer);
        library.borrowBook(1, 105, printer);   // limit reached
        library.returnBook(1, 103, printer);
        library.returnBook(1, 999, printer);   // not borrowed

        System.out.println("\nJohn's borrowed books: " + library.getBorrowedBooks(1));

        // ---------- Lab 2.1: bounded wildcards ----------
        System.out.println("\n===== Lab 2.1: Generic repositories & wildcards =====");
        List<EBook> ebooks = Arrays.asList(new EBook(201, "Java Streams", "A. Dev", 9000, 4.2),
                new EBook(202, "Generics Guide", "B. Coder", 6000, 3.1));
        System.out.println("Total price of ebooks (? extends Book): " + RepositoryUtils.totalPrice(ebooks));
        System.out.println("Cheapest ebook: " + RepositoryUtils.cheapest(ebooks).get().getTitle());

        RepositoryUtils.saveAll(library.getBooks(), ebooks);          // Repository<? super Book>
        List<Object> sink = new ArrayList<>();
        RepositoryUtils.copyAll(library.getBooks().findAll(), sink);  // List<Object> accepts Books (? super)
        System.out.println("Books in repository: " + library.getBooks().count() + ", copied to List<Object>: " + sink.size());

        // ---------- Lab 2.2: functional interfaces + streams ----------
        System.out.println("\n===== Lab 2.2: Functional interfaces & Streams =====");
        Filter<Book> cheap = b -> b.getPrice() < 10000;
        Filter<Book> byHarperLee = b -> b.getAuthor().equals("Harper Lee");
        RepositoryUtils.printAll("Cheap books by Harper Lee:",
                RepositoryUtils.filter(library.getBooks().findAll(), cheap.and(byHarperLee)));

        RepositoryUtils.printAll("Titles via method reference (Book::getTitle):",
                RepositoryUtils.transform(library.getBooks().findAll(), Book::getTitle));

        System.out.println("Sorted titles: " + library.sortedTitles());
        System.out.println("Books cheaper than 10000: " + library.booksCheaperThan(10000).size());
        System.out.println("Available books: " + library.availableBooks().size());
        System.out.println("Sorted member names: " + library.memberNamesSorted());
        System.out.println("Members by type: " + library.memberCountByType());
        System.out.println("Borrowed titles by member: " + library.borrowedTitlesByMember());
        System.out.println("Total value of borrowed books: " + library.totalValueOfBorrowedBooks());
        System.out.println("Late fees for 2 days (polymorphism): " + library.lateFeesForAllMembers(2));
        System.out.println("Member emails: " + library.mapMembers(Member::getEmail));
    }
}