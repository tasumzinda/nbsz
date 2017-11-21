package zw.org.nbsz.business.domain.util;

import zw.org.nbsz.business.util.StringUtils;

/**
 * Created by tasu on 5/10/17.
 */
public enum Frequency {

        ALWAYS(1), SOMETIMES(2);

        private final Integer code;

        Frequency(Integer code){
            this.code = code;
        }

        public static Frequency get(Integer code){
            switch (code){
                case 1:
                    return ALWAYS;
                case 2:
                    return SOMETIMES;
                default:
                    throw new IllegalArgumentException("Parameter provided to method is not recognized: " + code);
            }
        }

        public String getName(){
            return StringUtils.toCamelCase3(super.name());
        }
}
