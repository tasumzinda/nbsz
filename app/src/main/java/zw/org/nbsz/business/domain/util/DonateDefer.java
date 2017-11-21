package zw.org.nbsz.business.domain.util;

import zw.org.nbsz.business.util.StringUtils;

/**
 * Created by tasu on 5/23/17.
 */
public enum DonateDefer {

    DONATE(1), DEFER(2);

    private final Integer code;

    DonateDefer(Integer code){
        this.code = code;
    }

    public static DonateDefer get(Integer code){
        for(DonateDefer item : values()){
            if(item.getCode().equals(code)){
                return item;
            }
        }
        throw new IllegalArgumentException("Parameter provided is not recognized");
    }

    public Integer getCode(){
        return code;
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }

}
