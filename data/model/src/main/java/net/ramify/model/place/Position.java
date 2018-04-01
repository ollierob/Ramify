package net.ramify.model.place;

public interface Position {

    double latitude();

    double longitude();

    default double altitude() {
        return 0;
    }

}
