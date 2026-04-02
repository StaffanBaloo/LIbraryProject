public class Rules {
    final float overdueFeeStandard = 10;
    final float overdueFeePremium = 5;

    public static float feeByMembershipType (String type) {
        select(type.toLowerCase()){
            case "standard": {
                return overdueFeeStandard;
                break;
            }
            case "premium": {
                return overdueFeePremium;
                break;
            }
        }
    }
}
