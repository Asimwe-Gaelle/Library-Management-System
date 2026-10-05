public class StudentMember extends Member {

    private String studentId;
    private String course;

    public StudentMember(int memberId, String name, String email, String studentId, String course) {
        super(memberId, name, email);
        this.studentId = studentId;
        this.course = course;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public double calculateLateFees(int daysLate) {
        return daysLate * 500;
    }

    @Override
    public String toString() {
        return "StudentMember{" +
                "studentId='" + studentId + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}