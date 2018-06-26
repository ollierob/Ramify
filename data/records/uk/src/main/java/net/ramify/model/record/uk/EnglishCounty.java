package net.ramify.model.record.uk;

import net.ramify.model.place.County;

public class EnglishCounty extends County {

    public static final EnglishCounty YORKSHIRE = new EnglishCounty("Yorkshire");

    EnglishCounty(String name) {
        super(name);
    }

}
