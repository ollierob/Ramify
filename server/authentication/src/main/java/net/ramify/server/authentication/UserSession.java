package net.ramify.server.authentication;

import net.ramify.server.resource.Resource;

public interface UserSession {

    boolean permits(Resource resource);

}
