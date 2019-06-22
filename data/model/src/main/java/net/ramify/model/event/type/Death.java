package net.ramify.model.event.type;

public interface Death extends UniqueEvent {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

}
