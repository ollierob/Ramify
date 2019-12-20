package net.ramify.utils;

public class StringUtils {

    public static boolean isEmpty(final String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isBlank(final String s) {
        return s == null || s.isBlank();
    }

}
