package net.ramify.strategy.match;

public enum MatchResult {

    YES,
    NO,
    PASS;

    public boolean isMatch() {
        return this == YES;
    }

}

