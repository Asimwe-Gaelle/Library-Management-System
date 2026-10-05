public class StaffMember extends Member{
    private String staffId;
    private String department;

    public StaffMember(int memberId, String name, String email, String staffId, String department) {
        super(memberId, name, email);
        this.staffId = staffId;
        this.department = department;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public double calculateLateFees(int daysLate){
        return daysLate * 2000;
    }

    @Override
    public String toString() {
        return "StaffMember{" +
                "staffId='" + staffId + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
