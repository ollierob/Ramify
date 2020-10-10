package net.ramify.model.date.parse;

import java.time.chrono.ChronoLocalDate;

public interface DateParser {

    ChronoLocalDate parse(String date);

}
