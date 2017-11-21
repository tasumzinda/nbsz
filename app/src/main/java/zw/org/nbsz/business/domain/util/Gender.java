package zw.org.nbsz.business.domain.util;


import zw.org.nbsz.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/10/2016.
 */
public enum Gender {
    M(1), F(2);

    private final Integer code;

    Gender(Integer code) {
        this.code = code;
    }

    public static Gender get(Integer code) {
        switch (code) {
            case 1:
                return M;
            case 2:
                return F;
            default:
                return null;
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
