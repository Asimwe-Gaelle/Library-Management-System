public class Main {
    public static void main(String[] args) {

        Book book = new Book(
                101,
                "To Kill a MockingBird",
                "Harper Lee",
                8500
        );

        StudentMember student = new StudentMember(
                1,
                "John Doe",
                "johndoe@gmail.com",
                "ST001",
                "Economics"
        );

        StaffMember staff = new StaffMember(
                5,
                "Byiringiro Eric",
                "ericbyiringiro@gmail.com",
                "DEP001",
                "Information Technology"
        );


        System.out.println("Book Information:");
        System.out.println(book);

        System.out.println();

        System.out.println("Member Information:");
        System.out.println(student);
        System.out.println(staff);

        System.out.println("Student Late Fees: " + student.calculateLateFees(3));
        System.out.println("Publisher Late Fees: " + staff.calculateLateFees(2));
    }
}