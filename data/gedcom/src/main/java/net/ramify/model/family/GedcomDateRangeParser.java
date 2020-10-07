package net.ramify.model.family;

import net.ramify.model.date.ApproximateDateRange;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateRangeParser;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GedcomDateRangeParser implements DateRangeParser {

    private final DateRangeParser delegate;

    @Inject
    GedcomDateRangeParser(final DateRangeParser delegate) {
        this.delegate = delegate;
    }

    @CheckForNull
    @Override
    public DateRange get(final String key) {
        if (key.startsWith("A")) return Functions.ifNonNull(this.get(key.substring(1)), ApproximateDateRange::of);
        return delegate.get(key);
    }

}
