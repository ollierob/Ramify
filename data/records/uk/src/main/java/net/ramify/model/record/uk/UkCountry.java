package net.ramify.model.record.uk;

import net.ramify.model.place.Country;

/**
 * Current and historic UK countries.
 */
public class UkCountry extends Country {

    public static final UkCountry ENGLAND = new UkCountry("England");
    public static final UkCountry IRELAND = new UkCountry("Ireland");
    public static final UkCountry ISLE_OF_MAN = new UkCountry("Isle of Man");
    public static final UkCountry NORTHERN_IRELAND = new UkCountry("Northern Ireland");
    public static final UkCountry SCOTLAND = new UkCountry("Scotland");
    public static final UkCountry WALES = new UkCountry("Wales");
    public static final UkCountry UK = new UkCountry("United Kingdom");

    UkCountry(final String name) {
        super(name);
    }
}
