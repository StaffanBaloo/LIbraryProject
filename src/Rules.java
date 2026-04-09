public class Rules {
    // weekly overdue fees for a Standard/Premium member.
    final static int overdueFineStandard = 10;
    final static int overdueFinePremium = 5;
    //Duration of a loan in weeks for a standard/premium member.
    final static int loanDurationStandard =3;
    final static int loanDurationPremium = 5;

    public static int fineByMembershipType(String type) {
        switch (type.toLowerCase()){
            case "standard": {
                return overdueFineStandard;
                break;
            }
            case "premium": {
                return overdueFinePremium;
                break;
            }
        }
        return overdueFineStandard;
    }

    public static int weeksByMembershipType (String type) {
        switch (type.toLowerCase()){
            case "standard": {
                return loanDurationStandard;
                break;
            }
            case "premium": {
                return loanDurationPremium;
                break;
            }
        }
        return loanDurationStandard;
    }
}
