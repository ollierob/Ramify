package net.ramify.utils.objects;

public class ObjectUtils {

    public static <T> T firstNonNul(final T first, final T second) {
        return first != null ? first : second;
    }

}
