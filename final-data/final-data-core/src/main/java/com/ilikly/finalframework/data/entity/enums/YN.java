package com.ilikly.finalframework.data.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:32
 * @since 1.0
 */
public enum YN implements IEnum<Integer> {
    YES(1),
    NO(0);
    private final Integer code;


    @JsonCreator
    public static YN valueOf(int value) {
        for (YN yn : values()) {
            if (yn.code.equals(value)) return yn;
        }
        return null;
    }

    YN(Integer code) {
        this.code = code;
    }


    @Override
    public Integer getCode() {
        return code;
    }
}
