package net.ramify.utils.objects;

import java.util.Optional;

public abstract class OptionalBoolean {

    private OptionalBoolean() {
    }

    public abstract boolean isPresent();

    public abstract <T> Optional<T> map(BooleanObjectFunction<? extends T> function);

    private static class Defined extends OptionalBoolean {

        private final boolean value;

        private Defined(final boolean value) {
            this.value = value;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public <T> Optional<T> map(final BooleanObjectFunction<? extends T> function) {
            return Optional.ofNullable(function.apply(value));
        }

    }

    private static class Undefined extends OptionalBoolean {

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public <T> Optional<T> map(BooleanObjectFunction<? extends T> function) {
            return Optional.empty();
        }

    }

}
