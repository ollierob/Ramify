package net.ramify.server.authentication;

import net.ramify.authentication.UserSession;
import net.ramify.authentication.UserSessionContext;
import net.ramify.server.resource.Resource;

class StubSessionContext implements UserSessionContext {

    @Override
    public UserSession session() {
        return StubSession.INSTANCE;
    }

    private static final class StubSession implements UserSession {

        private static final StubSession INSTANCE = new StubSession();

        @Override
        public boolean permits(Resource resource) {
            return true;
        }
    }

}
