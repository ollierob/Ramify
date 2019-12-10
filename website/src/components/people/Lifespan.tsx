import * as React from "react";
import {Person} from "../../protobuf/generated/person_pb";
import {findBirth, findDeath} from "../event/Event";
import {DefaultPrefixWords, EmptyPrefixWords, formatYearRange, formatYearRanges, PrefixWords} from "../date/DateFormat";

export type LifespanWords = {
    life: string,
    born: string,
    died: string;
} & PrefixWords;

const DefaultLifespanWords: LifespanWords = {life: "Alive ", born: "Born ", died: "Died ", ...DefaultPrefixWords};
export const EmptyLifespanWords: LifespanWords = {life: "", born: "", died: "", ...EmptyPrefixWords};

export function renderLifespan(person: Person.AsObject, words?: Partial<LifespanWords>) {
    const useWords: LifespanWords = {...DefaultLifespanWords, ...words};
    const birth = findBirth(person.eventsList);
    const death = findDeath(person.eventsList);
    if (birth && death) return <span className="birth death">{useWords.life}{formatYearRanges(birth.date, death.date, useWords)}</span>;
    if (birth) return <span className="birth">{useWords.born}{formatYearRange(birth.date, useWords)}</span>;
    if (death) return <span className="death">{useWords.died}{formatYearRange(death.date, useWords)}</span>;
    return null;
}