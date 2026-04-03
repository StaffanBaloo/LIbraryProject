public class Rules {
    // weekly overdue fees for a Standard/Premium member.
    final static float overdueFeeStandard = 10;
    final static float overdueFeePremium = 5;
    //Duration of a loan in weeks for a standard/premium member.
    final static int loanDurationStandard =3;
    final static int loanDurationPremium = 5;

    public static float feeByMembershipType (String type) {
        switch (type.toLowerCase()){
            case "standard": {
                return overdueFeeStandard;
                break;
            }
            case "premium": {
                return overdueFeePremium;
                break;
            }
        }
        return overdueFeeStandard;
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
