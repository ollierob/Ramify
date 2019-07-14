package net.ramify.model.date;

import net.ramify.model.date.parse.DateParser;

import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

@Singleton
@XmlTransient
public class XmlDateParser implements DateParser {

    @Override
    public ChronoLocalDate parse(String date) {
        if (date.length() == 4) return LocalDate.of(Integer.parseInt(date), 1, 1);
        return LocalDate.parse(date);
    }

}
