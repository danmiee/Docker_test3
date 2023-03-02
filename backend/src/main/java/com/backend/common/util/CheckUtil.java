package com.backend.common.util;

import java.util.Objects;

public class CheckUtil {
    private CheckUtil(){

    }

    public static boolean isNullObject(Object... objects) {
        for (Object object : objects) {
            if (Objects.isNull(object)) return true;
        }
        return false;
    }

    public static boolean isEmptyString(String... strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty())  return true;
        }
        return false;
    }
}
