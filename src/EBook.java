// Subclass of Book - lets us demonstrate "? extends Book" wildcards
public class EBook extends Book {
    private double fileSizeMb;

    public EBook(int bookId, String title, String author, double price, double fileSizeMb) {
        super(bookId, title, author, price);
        this.fileSizeMb = fileSizeMb;
    }

    public double getFileSizeMb() {
        return fileSizeMb;
    }

    public void setFileSizeMb(double fileSizeMb) {
        this.fileSizeMb = fileSizeMb;
    }

    @Override
    public String toString() {
        return "EBook{" + super.toString() + ", fileSizeMb=" + fileSizeMb + '}';
    }
}