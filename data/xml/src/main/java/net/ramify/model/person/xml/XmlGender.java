package net.ramify.model.person.xml;

import net.ramify.model.person.gender.Gender;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlPerson.NAMESPACE)
public enum XmlGender {

    @XmlEnumValue("male")
    MALE(Gender.MALE),
    @XmlEnumValue("female")
    FEMALE(Gender.FEMALE),
    @XmlEnumValue("unknown")
    UNKNOWN(Gender.UNKNOWN);

    private final Gender gender;

    XmlGender(final Gender gender) {
        this.gender = gender;
    }

    public Gender gender() {
        return gender;
    }

}
