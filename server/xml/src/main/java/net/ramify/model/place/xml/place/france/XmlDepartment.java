package net.ramify.model.place.xml.place.france;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.Department;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlRegion;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlFrance.NAMESPACE, name = "department")
public class XmlDepartment extends XmlRegion<Department> {

    XmlDepartment() {
        super(Department.class);
    }

    @Nonnull
    @Override
    protected Department toPlace(PlaceGroupId groupId, PlaceHistory history, PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Department(this.placeId(context), this.name(), groupId, history);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return Collections.emptyList(); //TODO
    }

}
