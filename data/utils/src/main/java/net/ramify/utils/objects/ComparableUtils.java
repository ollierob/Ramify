package net.ramify.utils.objects;

public class ComparableUtils {

    public static <T extends Comparable<? super T>> T min(final T left, final T right) {
        return left.compareTo(right) <= 0 ? left : right;
    }

    public static <T extends Comparable<? super T>> T max(final T left, final T right) {
        return left.compareTo(right) >= 0 ? left : right;
    }

}
