import java.time.LocalDate;

public class Member {
    private int memberId;
    private String firstName, lastName, email, membershipType, status;
    private LocalDate membershipDate;

    public Member(int memberId, String firstName, String lastName, String email, String membershipType, String status, LocalDate membershipDate) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.membershipType = membershipType;
        this.status = status;
        this.membershipDate = membershipDate;
    }

    @Override
    public String toString() {
        String fullMember = "";
        fullMember += "id = " + memberId;
        fullMember += " | name = " + firstName + " " + lastName;
        fullMember += " | email = " + email;
        fullMember += " | type = " + membershipType;
        fullMember += " | status = " + status;
        fullMember += " | joined = " + membershipDate.toString();
        return fullMember;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }
}
