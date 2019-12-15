package net.ramify.model.record.type;

public interface RecordHandler<R> {

    R handle(BirthRecord birth);

    R handle(BaptismRecord baptism);

    R handle(LifeEventRecord residence);

    R handle(MarriageRecord marriage);

    R handle(DeathRecord death);

    R handle(PostDeathRecord record);

}
