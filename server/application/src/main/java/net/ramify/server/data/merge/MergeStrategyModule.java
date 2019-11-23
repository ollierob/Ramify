package net.ramify.server.data.merge;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import net.ramify.model.person.name.Name;
import net.ramify.strategy.person.match.name.MergedNameMatcher;
import net.ramify.strategy.person.match.name.NameMatcher;
import net.ramify.strategy.person.merge.name.DefaultNameMerger;
import net.ramify.strategy.person.merge.name.ForenameSurnameMerger;
import net.ramify.strategy.person.merge.name.NameMerger;

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
        return new DefaultNameMerger(new ForenameSurnameMerger((s1, s2) -> s1.length() > s2.length() ? s1.contains(s2) : s2.contains(s1))); //FIXME
    }

}
