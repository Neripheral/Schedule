package schedule;

import java.util.function.BooleanSupplier;

class Standby implements Schedule {
    private final Schedule schedule;

    public Standby(BooleanSupplier waitingFor) {
        schedule =
                new Repetition(
                        ()-> !waitingFor.getAsBoolean(),
                        new Step(()->{})
                );
    }

    @Override
    public boolean proceed() {
        return schedule.proceed();
    }

    @Override
    public void reset() {
        schedule.reset();
    }
}
