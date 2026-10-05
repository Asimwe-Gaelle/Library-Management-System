public class LibrarianMember {
    private String librarianName;

    public LibrarianMember(String librarianName) {
        this.librarianName = librarianName;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public void setLibrarianName(String librarianName) {
        this.librarianName = librarianName;
    }
    public double calculateLateFees(int daysLate){
        return daysLate * 2500;
    }

    @Override
    public String toString() {
        return "LibrarianMember{" +
                "librarianName='" + librarianName + '\'' +
                '}';
    }
}
