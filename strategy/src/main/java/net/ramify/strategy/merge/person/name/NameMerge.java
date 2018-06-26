package net.ramify.strategy.merge.person.name;

import net.ramify.model.person.name.Name;
import net.ramify.strategy.merge.Merge;

public interface NameMerge extends Merge<Name> {

    NameMerge DEFAULT = new EitherKnownName(new ReturnUnknownName());

}
