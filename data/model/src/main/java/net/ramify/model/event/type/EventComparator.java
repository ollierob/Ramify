package net.ramify.model.event.type;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;

import java.util.Comparator;

public interface EventComparator extends Comparator<Event> {

    Comparator<Event> BY_DATE = Comparator.comparing(Event::date, DateRange.COMPARE_BY_EARLIEST);

    Comparator<Event> DEFAULT = (e1, e2) -> {
        if (e1.isBirth() || e1.isBirth()) return compareBirth(e1, e2);
        if (e1.isLife() || e2.isLife()) return compareLife(e1, e2);
        if (e1.isDeath() || e2.isDeath()) return compareDeath(e1, e2);
        if (e1.isPostDeath() || e2.isPostDeath()) return comparePostDeath(e1, e2);
        return BY_DATE.compare(e1, e2);
    };

    static int compareBirth(final Event e1, final Event e2) {
        if (e1.isBirth() == e2.isBirth()) return 0;
        return e1.isBirth() ? -1 : 1;
    }

    static int compareLife(final Event e1, final Event e2) {
        if (e1.isLife() == e2.isLife()) return 0;
        if (e1.isLife() && e2.isBirth()) return 1;
        if (e1.isLife() && (e2.isDeath() || e2.isPostDeath())) return -1;
        return -compareLife(e2, e1);
    }

    static int compareDeath(final Event e1, final Event e2) {
        if (e1.isDeath() == e2.isDeath()) return 0;
        if (e1.isDeath()) return e2.isPostDeath() ? -1 : 1;
        return -compareDeath(e2, e1);
    }

    static int comparePostDeath(final Event e1, final Event e2) {
        if (e1.isPostDeath() == e2.isPostDeath()) return 0;
        return e1.isPostDeath() ? 1 : -1;
    }

}
