package net.ramify.model.record.civil.uk;

import net.ramify.model.date.ExactDate;
import net.ramify.model.record.SingleFamilyRecord;

public interface GeneralRegisterRecord extends SingleFamilyRecord {

    @Override
    ExactDate date();

}
