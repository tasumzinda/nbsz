package zw.org.nbsz.business.domain.util;

import zw.org.nbsz.business.util.StringUtils;

/**
 * Created by tasu on 8/6/17.
 */
public enum SearchParameters {

    DONOR_NUMBER(1), ID_NUMBER(2), NAME_AND_DATE_OF_BIRTH(3);

    private final Integer code;

    SearchParameters(Integer code){
        this.code = code;
    }

    public static SearchParameters get(Integer code){
        for(SearchParameters item : values()){
            if(item.code.equals(code))
                return item;
        }
        throw new IllegalArgumentException("Parameter provided is not recognized");
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}
