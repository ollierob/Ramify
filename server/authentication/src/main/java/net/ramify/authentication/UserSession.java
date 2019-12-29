package net.ramify.authentication;

import net.ramify.server.resource.Resource;

public interface UserSession {

    boolean permits(Resource resource);

    @Deprecated
    UserSession INTERNAL = r -> true;

}
