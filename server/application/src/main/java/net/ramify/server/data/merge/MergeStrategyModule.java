package net.ramify.server.data.merge;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import net.ramify.model.person.name.Name;
import net.ramify.strategy.match.person.name.MergedNameMatcher;
import net.ramify.strategy.match.person.name.NameMatcher;
import net.ramify.strategy.merge.person.name.NameMerger;
import net.ramify.strategy.merge.person.name.NameVariationMerger;

import javax.inject.Singleton;

public class MergeStrategyModule extends AbstractModule {

    @Provides
    @Singleton
    NameMatcher provideNameMatcher(final NameMerger<Name> merger) {
        return new MergedNameMatcher(merger);
    }

    @Provides
    @Singleton
    NameMerger<Name> provideNameMerge() {
        return new NameVariationMerger((n1, n2) -> n1.length() > n2.length() ? n1.contains(n2) : n2.contains(n1));
    }

}
