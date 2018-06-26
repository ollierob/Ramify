package net.ramify.strategy.merge.date;

import net.ramify.model.date.DateRange;
import net.ramify.strategy.merge.Merge;

public interface DateMerge extends Merge<DateRange> {

    DateMerge DEFAULT = new DatesIntersect();

}
