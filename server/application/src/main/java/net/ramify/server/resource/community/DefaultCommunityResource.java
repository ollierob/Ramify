package net.ramify.server.resource.community;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultCommunityResource implements CommunityResource {

    private final GroupsResource groups;
    private final ForumsResource forums;

    @Inject
    DefaultCommunityResource(
            final GroupsResource groups,
            final ForumsResource forums) {
        this.groups = groups;
        this.forums = forums;
    }

    @Override
    public GroupsResource groups() {
        return groups;
    }

    @Override
    public ForumsResource forums() {
        return forums;
    }

}
