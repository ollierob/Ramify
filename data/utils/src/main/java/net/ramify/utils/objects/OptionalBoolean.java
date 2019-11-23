package net.ramify.utils.objects;

import java.util.Optional;

public abstract class OptionalBoolean {

    private OptionalBoolean() {
    }

    public abstract boolean isPresent();

    public abstract boolean isTrue();

    public abstract <T> Optional<T> map(BooleanObjectFunction<? extends T> function);

    public static OptionalBoolean empty() {
        return Undefined.INSTANCE;
    }

    public static OptionalBoolean of(final boolean b) {
        return b ? Defined.TRUE : Defined.FALSE;
    }

    private static class Defined extends OptionalBoolean {

        static final Defined TRUE = new Defined(true);
        static final Defined FALSE = new Defined(false);

        private final boolean value;

        private Defined(final boolean value) {
            this.value = value;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public boolean isTrue() {
            return value;
        }

        @Override
        public <T> Optional<T> map(final BooleanObjectFunction<? extends T> function) {
            return Optional.ofNullable(function.apply(value));
        }

    }

    private static class Undefined extends OptionalBoolean {

        static final Undefined INSTANCE = new Undefined();

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public boolean isTrue() {
            return false;
        }

        @Override
        public <T> Optional<T> map(BooleanObjectFunction<? extends T> function) {
            return Optional.empty();
        }

    }

}
