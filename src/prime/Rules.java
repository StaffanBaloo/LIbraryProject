package prime;

import java.time.LocalDate;

public class Rules {
    // weekly overdue fees for a Standard/Premium member.
    private final static int overdueFineStandard = 10;
    private final static int overdueFinePremium = 5;
    //Duration of a loan in weeks for a standard/premium member.
    private final static int loanDurationStandard =3;
    private final static int loanDurationPremium = 5;
    //maximum days overdue for a standard/premium member before suspension.
    private final static int maxOverdueStandard = 14;
    private final static int maxOverduePremium = 28;
    //maximum fine for overdue book. This is currently set to be the same irrespective of membership type, but could change.
    private final static int maxFineStandard = 200;
    private final static int maxFinePremium = 200;


    public static int fineByMembershipType(String type) {
        int fine = overdueFineStandard;
        if(type.equalsIgnoreCase("premium")) fine = overdueFinePremium;
        return fine;
    }

    public static int weeksByMembershipType (String type) {
        int duration = loanDurationStandard;
        if(type.equalsIgnoreCase("premium")) duration = loanDurationPremium;
        return duration;
    }

    public static LocalDate suspensionDateByMembershipType(String type){
        LocalDate limitDate = LocalDate.now().minusDays(maxOverdueStandard);
        if(type.equalsIgnoreCase("premium")) limitDate = LocalDate.now().minusDays(maxOverduePremium);
        return limitDate;
    }

    public static int maxFineByMembershipType(String type){
        int maxFine = maxFineStandard;
        if(type.equalsIgnoreCase("premium")) maxFine=maxFinePremium;
        return maxFine;
    }

}
