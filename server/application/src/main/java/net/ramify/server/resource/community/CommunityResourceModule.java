package net.ramify.server.resource.community;

import com.google.inject.AbstractModule;

public class CommunityResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(CommunityRouterResource.class);
        this.bind(CommunityResource.class).to(DefaultCommunityResource.class);
        this.bind(ForumsResource.class).to(DefaultForumsResource.class);
        this.bind(GroupsResource.class).to(DefaultGroupsResource.class);
    }

}
