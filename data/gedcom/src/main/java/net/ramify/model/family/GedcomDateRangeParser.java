package net.ramify.model.family;

import net.ramify.model.date.AfterDate;
import net.ramify.model.date.ApproximateDateRange;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.parse.DateRangeParser;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

import static net.ramify.utils.StringUtils.isBlank;

@Singleton
public class GedcomDateRangeParser implements DateRangeParser {

    private final DateRangeParser delegate;

    @Inject
    GedcomDateRangeParser(final DateRangeParser delegate) {
        this.delegate = delegate;
    }

    @CheckForNull
    @Override
    public DateRange get(final String date) {
        if (isBlank(date)) return null;
        final var upper = date.toUpperCase();
        final var c0 = upper.charAt(0);
        if (isRangeChar(c0)) return this.getGedcomFormat(date);
        if (c0 == 'A' && isRangeChar(upper.charAt(1))) return Functions.ifNonNull(this.getGedcomFormat(date.substring(1)), ApproximateDateRange::of);
        return this.getDefault(date);
    }

    private static boolean isRangeChar(final char c) {
        return switch (c) {
            case '+', '-', '/' -> true;
            default -> false;
        };
    }

    private DateRange getGedcomFormat(final String pattern) {
        final var slash = pattern.indexOf('/');
        if (slash < 0) return this.getDefault(pattern);
        final var left = this.getDefault(pattern.substring(0, slash));
        final var right = this.getDefault(pattern.substring(slash + 1));
        if (left == null) return BeforeDate.strictlyBefore(right);
        if (right == null) return AfterDate.strictlyAfter(left);
        return ClosedDateRange.of(left, right);
    }

    private DateRange getDefault(final String date) {
        if (date.startsWith("+")) return delegate.get(date.substring(1)); //CE
        if (date.startsWith("-")) throw new UnsupportedOperationException(); //TODO BCE
        return delegate.get(date);
    }

    @Override
    public Optional<ExactDate> getExact(final String date) {
        return delegate.getExact(date);
    }

}
