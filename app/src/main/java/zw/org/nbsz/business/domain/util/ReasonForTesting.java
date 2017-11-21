package zw.org.nbsz.business.domain.util;

import zw.org.nbsz.business.util.StringUtils;

/**
 * Created by tasu on 5/15/17.
 */
public enum ReasonForTesting {

    VOLUNTARY(1), EMPLOYMENT(2), INSURANCE(3), MEDICAL_ADVICE(4), OTHER(5);

    private final Integer code;

    ReasonForTesting(Integer code){
        this.code = code;
    }

    public static ReasonForTesting get(Integer code){
        switch ((code)){
            case 1:
                return VOLUNTARY;
            case 2:
                return EMPLOYMENT;
            case 3:
                return INSURANCE;
            case 4:
                return MEDICAL_ADVICE;
            case 5:
                return OTHER;
            default:
                throw new IllegalArgumentException("Parameter provided to method is not recognized: " + code);
        }
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }

}
