package net.ramify.model.record.church.baptism;

import net.ramify.model.SingleFamilyRecord;
import net.ramify.model.event.Baptism;
import net.ramify.model.record.church.ChurchRecord;

import javax.annotation.Nonnull;

public interface ChurchBaptismRecord extends ChurchRecord, SingleFamilyRecord {

    @Nonnull
    default Baptism baptism() {
        return new Baptism(this.date(), this.address());
    }

}
