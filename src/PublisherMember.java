public class PublisherMember extends Member{
    private String companyName;

    public PublisherMember(int memberId, String name, String email, String companyName) {
        super(memberId, name, email);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public double calculateLateFees(int daysLate){
        return daysLate *  1500;
    }

    @Override
    public String toString() {
        return "PublisherMember{" +
                "companyName='" + companyName + '\'' +
                '}';
    }
}
