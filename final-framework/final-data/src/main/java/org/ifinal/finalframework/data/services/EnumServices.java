package org.ifinal.finalframework.data.services;

import org.ifinal.finalframework.annotation.Enums;
import org.ifinal.finalframework.annotation.IEnum;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.util.Classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class EnumServices {

    private EnumServices() {
    }

    public static List<String> enums() {
        return ServicesLoader.load(IEnum.class);
    }

    public static Map<String, String> descriptions() throws ClassNotFoundException {
        Map<String, String> result = new HashMap<>();
        for (String s : enums()) {
            Class<?> clazz = Classes.forName(s);
            Enum[] enumConstants = (Enum[]) clazz.getEnumConstants();
            for (Enum enumConstant : enumConstants) {
                result.put(Enums.getEnumI18NCode(enumConstant), ((IEnum) enumConstant).getDesc());
            }
        }

        return result;
    }


}