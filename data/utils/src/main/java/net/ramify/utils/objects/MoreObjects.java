package net.ramify.utils.objects;

public class MoreObjects {

    public static <T> T firstNonNul(final T first, final T second) {
        return first != null ? first : second;
    }

}
