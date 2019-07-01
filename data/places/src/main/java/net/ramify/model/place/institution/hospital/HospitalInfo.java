package net.ramify.model.place.institution.hospital;

import net.ramify.model.place.building.Hospital;
import net.ramify.model.place.institution.InstitutionInfo;

public interface HospitalInfo extends InstitutionInfo {

    @Override
    Hospital place();

}
