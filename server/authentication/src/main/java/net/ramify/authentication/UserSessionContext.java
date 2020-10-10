package net.ramify.authentication;

import javax.annotation.Nonnull;

public interface UserSessionContext {

    @Nonnull
    UserSession session() throws NotLoggedInException;

}
