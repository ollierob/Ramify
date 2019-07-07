package net.ramify.server.resource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Cached {

    boolean isPrivate() default true;

    int maxAgeSeconds() default 0;

    int maxAgeMinutes() default 0;

    int maxAgeHours() default 0;

    int maxAgeDays() default 0;

    boolean isImmutable() default false;

}
