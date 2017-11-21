package zw.org.nbsz.business.domain.util;

import zw.org.nbsz.business.util.StringUtils;

/**
 * Created by tasu on 5/11/17.
 */
public enum PackType {

    SINGLE(1), DRY(2), TRIPLE(3), QUADRUPLE(4);

    private final Integer code;

    PackType(Integer code){
        this.code = code;
    }

    public static PackType get(Integer code){
        switch (code){
            case 1:
                return SINGLE;
            case 2:
                return DRY;
            case 3:
                return TRIPLE;
            case 4:
                return QUADRUPLE;
            default:
                throw new IllegalArgumentException("Parameter passed to method not recognized: " + code);
        }
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}
