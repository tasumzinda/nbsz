package zw.org.nbsz.business.domain.util;

import zw.org.nbsz.business.util.StringUtils;

/**
 * Created by tasu on 9/11/17.
 */
public enum YesNoNA {

    YES(1), NO(2), NA(3);

    private final Integer code;

    YesNoNA(Integer code) {
        this.code = code;
    }

    public static YesNoNA get(Integer code) {
        for(YesNoNA item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Illegal parameter passed to method :" + code);
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }

    @Override
    public String toString() {
        return getName();
    }
}
